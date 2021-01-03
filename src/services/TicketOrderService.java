package services;

import java.util.ArrayList;
import java.util.List;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketStatus;
import core.domain.enums.TicketType;
import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ITicketOrderService;
import core.service.ITicketService;

public class TicketOrderService implements ITicketOrderService {
	private ITicketService ticketService;
	private IRepository<User> userRepository;
	private IRepository<Manifestation> manifestationRepository;

	public TicketOrderService(ITicketService ticketService, IRepository<User> userRepository,
			IRepository<Manifestation> manifestationRepository) {
		this.ticketService = ticketService;
		this.userRepository = userRepository;
		this.manifestationRepository = manifestationRepository;
	}

	@Override
	public List<Ticket> createTicketsFromOrder(TicketOrder ticketOrder) {
		//TODO: Proveriti slucaj kada je hash mapa prazna
		List<Ticket> tickets = new ArrayList<>();
		Buyer buyer = (Buyer) userRepository.read(ticketOrder.getBuyerId());
		Manifestation manifestation = manifestationRepository.read(ticketOrder.getManifestationId());
		
		int numberOfAllTickets = ticketOrder.getNumberOfOrderedTicketsMap().values().stream().reduce(0, Integer::sum);
		if(numberOfAllTickets <= manifestation.getSeats()) {
			ticketOrder.getNumberOfOrderedTicketsMap().forEach(
					(ticketType, numberOfTickets) -> 
					{
						for(int i = 0; i < numberOfTickets; ++i) {
							
							int price = getPriceOfTicket(manifestation.getRegularTicketPrice(), ticketType);
							int priceWithDiscount = getPriceOfTicketWithDiscount(price, buyer);
							
							Ticket ticket = new Ticket(
									"", 
									manifestation.getId(), 
									manifestation, 
									manifestation.getEventDate(),
									priceWithDiscount, 
									buyer.getId(), 
									buyer, 
									TicketStatus.Reserved, 
									ticketType);
							
							tickets.add(ticketService.create(ticket));
						}
					}
			);
		}
		
		return tickets;
	}
	
	public int getPriceOfTicket(int regularPrice, TicketType ticketType) {
		int typeModifier = ticketType.getModifier();
		
		return regularPrice * typeModifier;
	}
	
	public int getPriceOfTicketWithDiscount(int price, Buyer buyer) {
		double discount = (100 - buyer.getType().getDiscount()) / 100;
		int priceWithDiscount = (int) (price * discount);
		
		return priceWithDiscount;
	}
}
