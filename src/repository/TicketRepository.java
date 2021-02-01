package repository;

import java.util.UUID;

import core.domain.models.Ticket;
import core.exceptions.MissingEntityException;
import core.repository.ILazyLoader;
import repository.utils.loaders.LazyLoader;

public class TicketRepository extends Repository<Ticket> {
	public TicketRepository(DbContext context) {
		super(context, Ticket.class);
	}
	
	@Override
	public Ticket create(Ticket ticket) {
		Ticket addedTicket = super.create(ticket);
		loadDependencies(addedTicket);
		addedTicket.getBuyer().getTickets().add(addedTicket);
		
		return addedTicket;
	}
	
	
	@Override
	public Ticket update(Ticket ticketForUpdate) {
		Ticket updatedTicket = super.update(ticketForUpdate);
		loadDependencies(updatedTicket);
		
		return updatedTicket;
	}
	
	@Override
	public Ticket delete(UUID entityID) {
		Ticket ticketForDelition = entities.remove(entityID);
		
		if(ticketForDelition == null) {
			throw new MissingEntityException("Ticket does not exist");
		}
		
		ticketForDelition.getBuyer().getTickets().remove(ticketForDelition);
		entities.save();
		
		return ticketForDelition;
	}

	private void loadDependencies(Ticket ticket) {
		ILazyLoader loader = new LazyLoader(context);
		loader.loadDependencies(ticket);
	}

}
