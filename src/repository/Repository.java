package repository;

import java.util.List;
import java.util.UUID;

import core.domain.models.BaseEntity;
import core.repository.IRepository;

public class Repository<T extends BaseEntity> implements IRepository<T>{
	private DbSet<T> entities;
	
	@SuppressWarnings("unchecked")
	public Repository(DbContext context, Class<T> classType)
	{
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
	public boolean delete(UUID entityID) {
		boolean isDeleted = entities.remove(entityID);
		
		if(isDeleted == true) {
			entities.save();
		}
		
		return isDeleted;
	}

}
