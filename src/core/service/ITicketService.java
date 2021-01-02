package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.models.Manifestation;
import core.domain.models.Ticket;

public interface ITicketService extends ICrudService<Ticket> {
	List<Ticket> readByManifestationId(UUID manifestationId);
	List<Ticket> readReservedTicketsOfSellersManifestations(UUID sellerId);
	List<Ticket> readByBuyerId(UUID buyerId);
	List<Ticket> readReservedTicketsOfBuyer(UUID buyerId);
	Ticket updateCancelTicket(UUID ticketId);
}
