package services;

import java.util.ArrayList;
import java.util.List;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketType;
import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.service.IManifestationService;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import core.service.IUserService;

public class TicketOrderService implements ITicketOrderService {

	ITicketService ticketService;
	IUserService userService;
	IManifestationService manifestationService;


	public TicketOrderService(ITicketService ticketService, IUserService userService,
			IManifestationService manifestationService) {
		this.ticketService = ticketService;
		this.userService = userService;
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
						Ticket ticket = new Ticket();
						ticket.setBuyer(buyer);
						ticket.setBuyerId(buyer.getId());
						ticket.setManifestation(manifestation);
						ticket.setManifestationId(manifestation.getId());
						ticket.setType(ticketType);
						ticket.setPrice(getPriceOfTicket(manifestation.getRegularTicketPrice(), ticketType));
						
						tickets.add(ticket);
					}
				}
				);
		return tickets;
	}
	
	public int getPriceOfTicket(int regularPrice, TicketType ticketType) {
		int typeModifier = ticketType.getModifier();
		return regularPrice * typeModifier;
	}


}
