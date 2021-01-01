package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import core.domain.models.BaseEntity;
import core.repository.IDbSet;
import core.repository.IEntitiesDeserializator;
import core.repository.IEntitiesSerializator;


public class DbSet<T extends BaseEntity> implements IDbSet<T> {

	private Map<UUID, T> entities;
	private IEntitiesSerializator<T> serializator;
	private IEntitiesDeserializator<T> deserializator;
	private Class<T> classType;
	
	public DbSet(Class<T> classType)
    {
		this(classType, new DefaultEntitiesSerializator<T>(), new DefaultEntitiesDeserializator<T>(classType));
	}
	
	public DbSet(Class<T> classType, IEntitiesSerializator<T> serializator)
    {
		this(classType, serializator, new DefaultEntitiesDeserializator<T>(classType));
	}

	public DbSet(Class<T> classType, IEntitiesDeserializator<T> deserializator)
    {
		this(classType, new DefaultEntitiesSerializator<T>(), deserializator);
	}
	
	public DbSet(Class<T> classType, IEntitiesSerializator<T> serializator, IEntitiesDeserializator<T> deserializator)
	{
		this.entities = new HashMap<UUID, T>();
		this.classType = classType;
		this.serializator = serializator;
		this.deserializator = deserializator;
		loadEntities();
	}
	
	@Override
	public T add(T entity) {
		entities.put(entity.getId(), entity);
		
		return entity;
	}

	@Override
	public List<T> read() {
		return new ArrayList<T>(entities.values());
	}
	
	@Override
	public T read(UUID id) {
		return entities.get(id);
	}


	@Override
	public boolean remove(UUID id) {
		if(!entities.containsKey(id)) {
			return false;
		}
		
		entities.remove(id);
		return true;
	}

	@Override
	public void save() {
		try {
			serializator.Serialize(getFileName(), read());
		} catch (Exception e) {
			System.out.println("Problem while saving " + getDbSetName());
		}
	}

	@Override
	public Class<?> getEntityClassType() {
		return classType;
	}
	
	protected void loadEntities()
	{	
		try {
			List<T> listOfEntities = deserializator.Deserialize(getFileName());
		
			for(T entity : listOfEntities)
			{
				entities.put(entity.getId(), entity);
			}
						
		} catch (Exception e) {
			System.out.println("Problem while loading " + getDbSetName());
		}
	}
	
	protected String getDbSetName()
	{
		return classType.getSimpleName() + "s";
	}
	
	protected String getFileName()
	{
		return getDbSetName() + ".json";
	}
}