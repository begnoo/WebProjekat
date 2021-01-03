package repository;

import java.util.UUID;

import core.domain.models.Ticket;
import core.repository.IDependencyLoader;
import repository.utils.loaders.single.TicketDependencyLoader;

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
	public boolean delete(UUID entityID) {
		Ticket ticketForDelition = entities.read(entityID);
		boolean isDeleted = entities.remove(entityID);
		
		if(isDeleted == true) {
			ticketForDelition.getBuyer().getTickets().remove(ticketForDelition);
			entities.save();
		}
		
		return isDeleted;
	}

	private void loadDependencies(Ticket comment) {
		IDependencyLoader<Ticket> dependencyLoader = new TicketDependencyLoader(context);
		dependencyLoader.load(comment);
	}

}
