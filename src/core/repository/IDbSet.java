package core.repository;

import java.util.List;
import java.util.UUID;

import core.domain.models.BaseEntity;

public interface IDbSet<T extends BaseEntity> {
	T add(T entity);
	
	List<T> read();
	
	boolean remove(UUID id);
	
	void save();
}
