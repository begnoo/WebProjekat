package repository.utils.loaders.single;

import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class TicketDependencyLoader implements IDependencyLoader<Ticket> {
	private DbSet<Manifestation> manifestations;
	private DbSet<User> users;
	
	@SuppressWarnings("unchecked")
	public TicketDependencyLoader(DbContext context)
	{
		manifestations = (DbSet<Manifestation>) context.getSet(Manifestation.class);
		users = (DbSet<User>) context.getSet(User.class);
	}

	@Override
	public void load(Ticket ticket) {
		Manifestation manifestation = manifestations.read(ticket.getManifestationId());
		ticket.setManifestation(manifestation);

		Buyer buyer = (Buyer) users.read(ticket.getBuyerId());
		ticket.setBuyer(buyer);
	}
}
