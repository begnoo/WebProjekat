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
		this.classType = classType;
		this.entities = new HashMap<UUID, T>();
		this.serializator = new DefaultEntitiesSerializator<T>();
		this.deserializator = new DefaultEntitiesDeserializator<T>(this.classType);
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
