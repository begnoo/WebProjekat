package services;

import java.util.List;
import java.util.UUID;

import core.domain.models.BaseEntity;
import core.repository.IRepository;
import core.service.ICrudService;

public class CrudService<T extends BaseEntity> implements ICrudService<T> {
	
	protected IRepository<T> repository;
	
	public CrudService(IRepository<T> repository) {
		this.repository = repository;
	}

	@Override
	public T create(T entity) {
		return repository.create(entity);
	}

	@Override
	public List<T> read() {
		return repository.read();
	}

	@Override
	public T read(UUID id) {
		return repository.read(id);
	}

	@Override
	public T update(T entityForUpdate) {
		return repository.update(entityForUpdate);
	}

	@Override
	public boolean delete(UUID entityID) {
		return repository.delete(entityID);
	}

}
