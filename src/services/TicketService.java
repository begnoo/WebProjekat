package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.TicketStatus;
import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ITicketService;

public class TicketService extends CrudService<Ticket> implements ITicketService {

	IRepository<User> userRepository;
	IRepository<Manifestation> manifestationRepository;


	public TicketService(IRepository<Ticket> repository, IRepository<User> userRepository,
			IRepository<Manifestation> manifestationRepository) {
		super(repository);
		this.userRepository = userRepository;
		this.manifestationRepository = manifestationRepository;
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
		updateBuyerPoints(buyer, earnedPoints);
		
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
		updateBuyerPoints(buyer, penaltyPoints);

		ticket.setStatus(TicketStatus.Canceled);
		return repository.update(ticket);
	}
	
	private boolean checkIfNowIsSevenDaysBeforeEventDate(LocalDateTime eventDate) {
		return LocalDateTime.now()
							.plusDays(7)
							.isBefore(eventDate);
	}
	
	private void updateBuyerPoints(Buyer buyer, int additionalPoints) {
		int newBuyerPoints = buyer.getPoints() + additionalPoints;
		if(newBuyerPoints < 0)
		{
			newBuyerPoints = 0;
		}
		
		buyer.setPoints(newBuyerPoints);
		userRepository.update(buyer);
	}
	
	private boolean updateNumberOfSeatsForManifestation(Manifestation manifestation, int additionSeats) {
		int newNumberOfManifestationSeats = manifestation.getSeats() + additionSeats;
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
