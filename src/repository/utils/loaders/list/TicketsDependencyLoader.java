package repository.utils.loaders.list;

import java.util.List;

import core.domain.models.Ticket;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.utils.loaders.single.TicketDependencyLoader;

public class TicketsDependencyLoader implements IDependencyLoader<List<Ticket>> {
	private TicketDependencyLoader ticketDependencyLoader;
	
	public TicketsDependencyLoader(DbContext context)
	{
		ticketDependencyLoader = new TicketDependencyLoader(context);
	}

	@Override
	public void load(List<Ticket> tickets) {
		for(Ticket ticket : tickets)
		{
			ticketDependencyLoader.load(ticket);
		}
	}
}
