package core.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketType;
import core.domain.models.Ticket;

public interface ITicketOrderService {
	List<Ticket> createTicketsFromOrder(TicketOrder ticketOrder);
	HashMap<TicketType, Integer> getTicketPrices(int regularPrice, UUID buyerTypeId);
}
