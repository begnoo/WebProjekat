package core.repository;

import java.util.List;
import java.util.UUID;

import core.domain.models.BaseEntity;

public interface IRepository<T extends BaseEntity> {
	T create(T entity);
	
	List<T> read();
	
	T read(UUID id);
	
	T update(T entityForUpdate);
	
	T delete(UUID entityID);
	
	IDbSetStream<T> getStream();
}
