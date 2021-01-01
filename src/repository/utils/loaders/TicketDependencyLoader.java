package repository.utils.loaders;

import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class TicketDependencyLoader implements IDependencyLoader {
	private DbSet<Ticket> tickets;
	private DbSet<Manifestation> manifestations;
	private DbSet<User> users;
	
	@SuppressWarnings("unchecked")
	public TicketDependencyLoader(DbContext context)
	{
		tickets = (DbSet<Ticket>) context.getSet(Ticket.class);
		manifestations = (DbSet<Manifestation>) context.getSet(Manifestation.class);
		users = (DbSet<User>) context.getSet(User.class);
	}

	@Override
	public void Load() {
		for(Ticket ticket : tickets.read())
		{
			Manifestation manifestation = manifestations.read(ticket.getManifestationId());
			ticket.setManifestation(manifestation);

			Buyer buyer = (Buyer) users.read(ticket.getBuyerId());
			ticket.setBuyer(buyer);
		}
	}
}
