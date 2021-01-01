package repository.utils.loaders;

import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class BuyerDependencyLoader implements IDependencyLoader {
	private DbSet<User> users;
	private DbSet<Ticket> tickets;
	private DbSet<BuyerType> buyerTypes;
	
	@SuppressWarnings("unchecked")
	public BuyerDependencyLoader(DbContext context)
	{
		users = (DbSet<User>) context.getSet(User.class);
		tickets = (DbSet<Ticket>) context.getSet(Ticket.class);
		buyerTypes = (DbSet<BuyerType>) context.getSet(BuyerType.class);
	}

	@Override
	public void Load() {
		for(User user : users.read()) {
			if(user.getRole() != UserRole.Buyer) {
				continue;
			}
			
			Buyer buyer = (Buyer) user;
		
			LoadTicketsForBuyer(buyer);
			LoadTypeForBuyer(buyer);
		}
		
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
