package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketStatus;
import core.domain.enums.TicketType;
import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.service.IManifestationService;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import core.service.IUserService;

public class TicketOrderService implements ITicketOrderService {

	private ITicketService ticketService;
	private IUserService userService;
	private IManifestationService manifestationService;


	public TicketOrderService(ITicketService ticketService, IUserService userService,
			IManifestationService manifestationService) {
		this.ticketService = ticketService;
		this.userService = userService;
		this.manifestationService = manifestationService;
	}

	@Override
	public List<Ticket> createTicketsFromOrder(TicketOrder ticketOrder) {
		//TODO: Proveriti slucaj kada je hash mapa prazna
		List<Ticket> tickets = new ArrayList<>();
		Buyer buyer = (Buyer) userService.read(ticketOrder.getBuyerId());
		Manifestation manifestation = manifestationService.read(ticketOrder.getManifestationId());
		ticketOrder.getNumberOfOrderedTicketsMap().forEach(
				(ticketType, numberOfTickets) -> 
				{
					for(int i = 0; i < numberOfTickets; ++i) {
						
						int price = getPriceOfTicket(manifestation.getRegularTicketPrice(), ticketType);
						int priceWithDiscount = getPriceOfTicketWithDiscount(price, buyer);
						
						Ticket ticket = new  Ticket(
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
		
		return tickets;
	}
	
	public int getPriceOfTicket(int regularPrice, TicketType ticketType) {
		int typeModifier = ticketType.getModifier();
		return regularPrice * typeModifier;
	}
	
	public int getPriceOfTicketWithDiscount(int price, Buyer buyer) {
		double discount = (100 - buyer.getType().getDiscount()) / 100;
		int priceWithDiscount = (int) (price*discount);
		return priceWithDiscount;
	}


}
