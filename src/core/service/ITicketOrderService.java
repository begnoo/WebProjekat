package core.service;

import java.util.List;

import core.domain.dto.TicketOrder;
import core.domain.models.Ticket;

public interface ITicketOrderService {
	List<Ticket> createTicketsFromOrder(TicketOrder ticketOrder);
}
