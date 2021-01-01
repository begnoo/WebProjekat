package services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.models.Ticket;
import core.repository.IRepository;
import core.service.ITicketService;

public class TicketService extends CrudService<Ticket> implements ITicketService{

	public TicketService(IRepository<Ticket> repository) {
		super(repository);
	}
	
    @Override
	public List<Ticket> readByManifestationId(UUID manifestationId) {
        return repository.read()
            .stream()
            .filter(ticket -> manifestationId.equals(ticket.getManifestationId()))
            .collect(Collectors.toList());
    }
	
}
