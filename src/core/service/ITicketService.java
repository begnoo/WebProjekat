package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.models.Ticket;

public interface ITicketService extends ICrudService<Ticket>{
	List<Ticket> readByManifestationId(UUID manifestationId);
}
