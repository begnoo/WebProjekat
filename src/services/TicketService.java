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
		return repository.read().stream().filter(ticket -> manifestationId.equals(ticket.getManifestationId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> readByBuyerId(UUID buyerId) {
		return repository.read().stream().filter(ticket -> buyerId.equals(ticket.getBuyerId()))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<Ticket> readReservedTicketsOfBuyer(UUID buyerId) {
		return readByBuyerId(buyerId)
				.stream()
				.filter(ticket -> ticket.getStatus() == TicketStatus.Reserved)
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> readReservedTicketsOfSellersManifestations(UUID sellerId) {
		Seller seller = (Seller) userRepository.read(sellerId);
		if (seller == null) {
			return null;
		}

		// TODO: Pogledati da li ovo moze nekako lepse da se odradi
		List<Ticket> tickets = new ArrayList<>();
		seller.getManifestations().forEach(manifestation -> tickets.addAll(readByManifestationId(manifestation.getId())
				.stream().filter(ticket -> ticket.getStatus() == TicketStatus.Reserved).collect(Collectors.toList())));

		return tickets;
	}

	@Override
	public Ticket create(Ticket ticket) {
		//TODO: Dodati uniqeId
		Buyer buyer = (Buyer) userRepository.read(ticket.getBuyerId());
		ticket.getBuyer().setPoints(buyer.getPoints() + getPointValue(ticket.getPrice()));
		userRepository.update(buyer);
		
		int numberOfManifestationSeats = ticket.getManifestation().getSeats();
		int newNumberOfManifestationSeats = (numberOfManifestationSeats - 1 == 0) ?  numberOfManifestationSeats : 0;
		ticket.getManifestation().setSeats(newNumberOfManifestationSeats);
		manifestationRepository.update(ticket.getManifestation());
		
		ticket.setStatus(TicketStatus.Reserved);
		return repository.create(ticket);
	}
	
	private int getPointValue(int price) {
		return price/1000 * 133;
	}

	@Override
	public Ticket cancelTicket(UUID ticketId) {
		Ticket ticket = repository.read(ticketId);
		if(ticket != null && checkIfSevenDaysBeforeEventDate(ticket.getManifestationDate())) {
			Buyer buyer = (Buyer) userRepository.read(ticket.getBuyerId());
			int pointsWithPenalty = buyer.getPoints() - getPointValue(ticket.getPrice())*4;
			int newPoints = (pointsWithPenalty >= 0) ? pointsWithPenalty : 0;
			ticket.getBuyer().setPoints(newPoints);
			userRepository.update(buyer);
			
			int numberOfManifestationSeats = ticket.getManifestation().getSeats();
			ticket.getManifestation().setSeats(numberOfManifestationSeats + 1);
			manifestationRepository.update(ticket.getManifestation());
			
			
			ticket.setStatus(TicketStatus.Reserved);
		}
		return repository.update(ticket);
	}
	
	private boolean checkIfSevenDaysBeforeEventDate(LocalDateTime eventDate) {
		return LocalDateTime.now().plusDays(7).isBefore(eventDate);
	}


	
}
