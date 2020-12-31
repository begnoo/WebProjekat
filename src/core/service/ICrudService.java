package core.service;

import java.util.List;
import java.util.UUID;

public interface ICrudService<T> {
	T create(T entity);
	
	List<T> read();
	
	T read(UUID id);
	
	T update(T entityForUpdate);
	
	boolean delete(UUID entityID);
}