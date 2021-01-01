package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.models.BaseEntity;

public interface ICrudService<T extends BaseEntity> {
	T create(T entity);
	
	List<T> read();
	
	T read(UUID id);
	
	T update(T entityForUpdate);
	
	boolean delete(UUID entityID);
}
