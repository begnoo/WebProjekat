package repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import core.domain.models.BuyerType;
import core.domain.models.Comment;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IDbSet;
import core.repository.ILazyLoader;
import repository.utils.deserializators.UsersDeserializator;
import repository.utils.loaders.LazyLoader;


@SuppressWarnings("unused")
public class DbContext {
	private IDbSet<User> users;
	private IDbSet<BuyerType> buyerTypes;
	private IDbSet<Location> locations;
	private IDbSet<Manifestation> manifestations;
	private IDbSet<Ticket> tickets;
	private IDbSet<Comment> comments;
	
	public DbContext()
	{
		initializeSets();
		loadDependencies();
	}
	
	private void initializeSets()
	{
		this.users = new DbSet<User>(User.class, new UsersDeserializator());
		this.buyerTypes = new DbSet<BuyerType>(BuyerType.class);
		this.locations = new DbSet<Location>(Location.class);
		this.manifestations = new DbSet<Manifestation>(Manifestation.class);
		this.tickets = new DbSet<Ticket>(Ticket.class);
		this.comments = new DbSet<Comment>(Comment.class);
	}
	
	private void loadDependencies()
	{
		loadDependenciedForDbSet(users);
		loadDependenciedForDbSet(comments);
		loadDependenciedForDbSet(manifestations);
		loadDependenciedForDbSet(tickets);
	}
	
	private void loadDependenciedForDbSet(IDbSet<?> set) {
		List<Object> entities = getAllEntitiesFromDbSet(set);
		ILazyLoader loader = new LazyLoader(this);
		
		for(Object entity : entities) {
			loader.loadDependencies(entity);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Object> getAllEntitiesFromDbSet(IDbSet<?> set) {
		List<Object> entities = new ArrayList<Object>();
		
		try {
			Field entitiesField = set.getClass().getDeclaredField("entities");
			entitiesField.setAccessible(true);
			entities = new ArrayList(((HashMap<UUID, Object>) entitiesField.get(set)).values());
			entitiesField.setAccessible(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return entities;
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
		} catch (Exception e) {
			System.out.println("Can't get db set for: " + classType.getCanonicalName());
		}
		
		return null;
	}
}
