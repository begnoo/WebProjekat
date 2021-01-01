package repository.utils.loaders.single;

import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class BuyerDependencyLoader implements IDependencyLoader<User> {
	private DbSet<Ticket> tickets;
	private DbSet<BuyerType> buyerTypes;
	
	@SuppressWarnings("unchecked")
	public BuyerDependencyLoader(DbContext context)
	{
		tickets = (DbSet<Ticket>) context.getSet(Ticket.class);
		buyerTypes = (DbSet<BuyerType>) context.getSet(BuyerType.class);
	}

	@Override
	public void load(User user) {
		if(user.getRole() != UserRole.Buyer) {
			return;
		}
		
		Buyer buyer = (Buyer) user;
	
		LoadTicketsForBuyer(buyer);
		LoadTypeForBuyer(buyer);
	}

	private void LoadTicketsForBuyer(Buyer buyer)
	{
		for(Ticket ticket : tickets.read()) {
			if(ticket.getBuyerId().equals(buyer.getId())) {
				buyer.getTickets().add(ticket);
			}
		}
	}
	
	private void LoadTypeForBuyer(Buyer buyer) {
		BuyerType type = buyerTypes.read(buyer.getBuyerTypeId());
		buyer.setType(type);
	}
}