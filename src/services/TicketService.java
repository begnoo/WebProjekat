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
import core.exceptions.BadLogicException;
import core.exceptions.MissingEntityException;
import core.repository.IRepository;
import core.service.ITicketService;
import core.service.IUserTicketManifestationMediator;
import services.utils.RandomUtils;

public class TicketService extends CrudService<Ticket> implements ITicketService {
	private IUserTicketManifestationMediator mediator;
	
	public TicketService(IRepository<Ticket> repository, IUserTicketManifestationMediator mediator) {
		super(repository);
		
		this.mediator = mediator;
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
		Seller seller = (Seller) mediator.readUser(sellerId);
		if (seller == null) {
			throw new MissingEntityException(String.format("Seller with id = %s does not exists.", sellerId));
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
		Manifestation manifestation = ticket.getManifestation();		
		if(mediator.updateNumberOfSeats(manifestation, -1) == null) {
			throw new BadLogicException("There is not enough seats left.");
		}
		
		Buyer buyer = ticket.getBuyer();
		int earnedPoints = getPointValue(ticket.getPrice());
		mediator.updateBuyerPointsFor(buyer, earnedPoints);
		
		ticket.setStatus(TicketStatus.Reserved);
		String uniqueId = "";
		do {
			uniqueId = RandomUtils.getRandomString(10);
		} while(isUniqueIdTaken(uniqueId));
		ticket.setUniqueId(uniqueId);
		
		return repository.create(ticket);
	}
	
	private boolean isUniqueIdTaken(String id) {
		return read().stream().anyMatch(ticket -> ticket.getUniqueId().equals(id));
	}
	
	@Override
	public Ticket delete(UUID ticketId) {
		Ticket ticket = repository.read(ticketId);
		if(ticket == null) {
			throw new MissingEntityException(String.format("Ticket with id = %s does not exists.", ticketId));
		}
		
		boolean hasNotStartedAndTicketIsReserved = ticket.getManifestationDate().isAfter(LocalDateTime.now()) && ticket.getStatus() == TicketStatus.Reserved;
		if(hasNotStartedAndTicketIsReserved) {
			returnSeatsAndTakeUserPoints(ticket, -1);
		}
		
		return super.delete(ticketId);
	}
	
	@Override
	public Ticket cancelTicket(UUID ticketId) {
		Ticket ticket = repository.read(ticketId);
		if(ticket == null) {
			throw new MissingEntityException(String.format("Ticket with id = %s does not exists.", ticketId));
		}

		if(!checkIfNowIsSevenDaysBeforeEventDate(ticket.getManifestationDate())) {
			throw new BadLogicException("You can not cancel your ticket anymore.");
		}
			
		returnSeatsAndTakeUserPoints(ticket, -4);

		ticket.setStatus(TicketStatus.Canceled);
		return repository.update(ticket);
	}
	
	private boolean checkIfNowIsSevenDaysBeforeEventDate(LocalDateTime eventDate) {
		return LocalDateTime.now()
							.plusDays(7)
							.isBefore(eventDate);
	}
	
	private void returnSeatsAndTakeUserPoints(Ticket ticket, int pointCoenficient) {
		Manifestation manifestation = mediator.readManifestation(ticket.getManifestationId());
		mediator.updateNumberOfSeats(manifestation, 1);

		Buyer buyer = ticket.getBuyer();
		int penaltyPoints = getPointValue(ticket.getPrice()) * pointCoenficient;
		mediator.updateBuyerPointsFor(buyer, penaltyPoints);
	}
	
	private int getPointValue(int price) {
		return price / 1000 * 133;
	}
}
