package repository;

import java.util.List;
import java.util.UUID;

import core.domain.models.BaseEntity;
import core.exceptions.MissingEntityException;
import core.repository.IDbSetStream;
import core.repository.IRepository;

public class Repository<T extends BaseEntity> implements IRepository<T>{
	protected DbContext context;
	protected DbSet<T> entities;
	
	@SuppressWarnings("unchecked")
	public Repository(DbContext context, Class<T> classType)
	{
		this.context = context;
		this.entities = (DbSet<T>) context.getSet(classType);
	}
	
	@Override
	public T create(T entity) {
		entities.add(entity);
		entities.save();
		
		return entity;
	}

	@Override
	public List<T> read() {
		return entities.read();
	}

	@Override
	public T read(UUID id) {
		return entities.read(id);
	}

	@Override
	public T update(T entityForUpdate) {
		T entity = read(entityForUpdate.getId());
		entity = entityForUpdate;
		entities.save();
		
		return entity;
	}

	@Override
	public T delete(UUID entityID) {
		T deletedEntity = entities.remove(entityID);
		
		if(deletedEntity != null) {
			entities.save();
		} else {
			throw new MissingEntityException("Entity does not exist");
		}
		
		return deletedEntity;
	}

	@Override
	public IDbSetStream<T> getStream() {
		return entities.getStream();
	}

}
