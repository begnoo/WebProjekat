package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.TicketStatus;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ITicketService;

public class TicketService extends CrudService<Ticket> implements ITicketService {

	IRepository<User> userRepository;
	IRepository<Manifestation> manifestationRepository;
	IRepository<BuyerType> buyerTypeRepository;


	public TicketService(IRepository<Ticket> repository, IRepository<User> userRepository,
			IRepository<Manifestation> manifestationRepository, IRepository<BuyerType> buyerTypeRepository) {
		super(repository);
		this.userRepository = userRepository;
		this.manifestationRepository = manifestationRepository;
		this.buyerTypeRepository = buyerTypeRepository;
	}

	@Override
	public List<Ticket> readByManifestationId(UUID manifestationId) {
		return repository.read().stream()
				.filter(ticket -> manifestationId.equals(ticket.getManifestationId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> readByBuyerId(UUID buyerId) {
		return repository.read().stream()
				.filter(ticket -> buyerId.equals(ticket.getBuyerId()))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Ticket> readReservedTicketsOfBuyer(UUID buyerId) {
		return readByBuyerId(buyerId).stream()
				.filter(ticket -> ticket.getStatus() == TicketStatus.Reserved)
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> readReservedTicketsOfSellersManifestations(UUID sellerId) {
		Seller seller = (Seller) userRepository.read(sellerId);
		if (seller == null) {
			return null;
		}

		List<Ticket> tickets = new ArrayList<>();
		for(Manifestation manifestation : seller.getManifestations())
		{
			tickets.addAll(readReservedTicketsOfBuyerOfManifestation(manifestation.getId()));
		}

		return tickets;
	}
	
	private List<Ticket> readReservedTicketsOfBuyerOfManifestation(UUID manifestationId)
	{
		return readByManifestationId(manifestationId)
				.stream()
				.filter(ticket -> ticket.getStatus() == TicketStatus.Reserved)
				.collect(Collectors.toList());
	}

	@Override
	public Ticket create(Ticket ticket) {
		//TODO: Dodati uniqeId
		
		Manifestation manifestation = ticket.getManifestation();
		if(!updateNumberOfSeatsForManifestation(manifestation, -1)) {
			return null;
		}
		
		Buyer buyer = ticket.getBuyer();
		int earnedPoints = getPointValue(ticket.getPrice());
		updateBuyerPointsAndUpgradeBuyerType(buyer, earnedPoints);
		
		ticket.setStatus(TicketStatus.Reserved);
		return repository.create(ticket);
	}
	
	@Override
	public Ticket cancelTicket(UUID ticketId) {
		Ticket ticket = repository.read(ticketId);
		if(ticket == null || !checkIfNowIsSevenDaysBeforeEventDate(ticket.getManifestationDate())) {
			return null;
		}
			
		Manifestation manifestation = manifestationRepository.read(ticket.getManifestationId());
		updateNumberOfSeatsForManifestation(manifestation, 1);
		
		Buyer buyer = ticket.getBuyer();
		int penaltyPoints = getPointValue(ticket.getPrice()) * -4;
		updateBuyerPointsAndUpgradeBuyerType(buyer, penaltyPoints);

		ticket.setStatus(TicketStatus.Canceled);
		return repository.update(ticket);
	}
	
	private boolean checkIfNowIsSevenDaysBeforeEventDate(LocalDateTime eventDate) {
		return LocalDateTime.now()
							.plusDays(7)
							.isBefore(eventDate);
	}
	
	private void updateBuyerPointsAndUpgradeBuyerType(Buyer buyer, int additionalPoints) {
		updateBuyerPoints(buyer, additionalPoints);
		upgradeBuyerType(buyer, buyer.getPoints());
		userRepository.update(buyer);
	}
	
	private void updateBuyerPoints(Buyer buyer, int additionalPoints) {
		int newBuyerPoints = buyer.getPoints() + additionalPoints;
		if(newBuyerPoints < 0)
		{
			newBuyerPoints = 0;
		}
		buyer.setPoints(newBuyerPoints);
	}
	
	private void upgradeBuyerType(Buyer buyer, int points) {
		List<BuyerType> buyerTypes = buyerTypeRepository.read();
		buyerTypes.sort((buyerType1, buyerType2) -> buyerType1.getMinimumPoints() - buyerType2.getMinimumPoints());
		
		for(BuyerType buyerType : buyerTypes) {
			if(buyerType.getMinimumPoints() <= points) {
				buyer.setBuyerTypeId(buyerType.getId());
			}
		}
	}
	
	private boolean updateNumberOfSeatsForManifestation(Manifestation manifestation, int additionalSeats) {
		int newNumberOfManifestationSeats = manifestation.getSeats() + additionalSeats;
		if(newNumberOfManifestationSeats < 0) {
			return false;
		}
		
		manifestation.setSeats(newNumberOfManifestationSeats);
		return manifestationRepository.update(manifestation) != null;
	}
	
	private int getPointValue(int price) {
		return price / 1000 * 133;
	}
}
