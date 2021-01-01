package repository;

import java.lang.reflect.Field;

import core.domain.models.BuyerType;
import core.domain.models.Comment;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IDbSet;

public class DbContext {
	private IDbSet<User> users;
	private IDbSet<BuyerType> buyerTypes;
	private IDbSet<Location> locations;
	private IDbSet<Manifestation> manifestations;
	private IDbSet<Ticket> tickets;
	private IDbSet<Comment> comments;
	
	public DbContext()
	{
		this.users = new DbSet<User>(User.class, new UsersDeserializator());
		this.buyerTypes = new DbSet<BuyerType>(BuyerType.class);
		this.locations = new DbSet<Location>(Location.class);
		this.manifestations = new DbSet<Manifestation>(Manifestation.class);
		this.tickets = new DbSet<Ticket>(Ticket.class);
		this.comments = new DbSet<Comment>(Comment.class);
	}
	
	public IDbSet<?> getSet(Class<?> classType)
	{
		try {
			Class<?> thisClass = this.getClass(); 
			Field[] dbSetFields = thisClass.getDeclaredFields();
			for(Field dbSetField : dbSetFields)
			{
				DbSet<?> dbSet = (DbSet<?>)dbSetField.get(this);
				if(dbSet.getEntityClassType() == classType)
				{
					return dbSet;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
