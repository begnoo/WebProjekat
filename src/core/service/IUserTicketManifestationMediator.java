package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;

public interface IUserTicketManifestationMediator {
	
	// To -> Ticket
	List<Ticket> readTicketsByBuyerId(UUID buyerId);

	List<Ticket> readTicketsByManifestaionId(UUID manifestationId);
	
	Ticket deleteTicket(UUID ticketId);

	// To -> User
	User readUser(UUID userId);
	
	User updateBuyerPointsFor(Buyer buyer, int earnedPoints);
	
	// To -> Manifestation
	Manifestation updateNumberOfSeats(Manifestation manifestation, int seats);
	
	Manifestation readManifestation(UUID manifestationId);
	
	Manifestation deleteManifestation(UUID manifestationId);

}
