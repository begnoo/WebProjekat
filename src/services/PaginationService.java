package services;

import java.util.List;
import java.util.stream.Collectors;

import core.domain.dto.Page;
import core.domain.models.BaseEntity;
import core.repository.IRepository;
import core.service.IPaginationService;

public class PaginationService<T extends BaseEntity> implements IPaginationService<T> {

	private IRepository<T> repository;
	
	public PaginationService(IRepository<T> repository)
	{
		this.repository = repository;
	}
	
	@Override
	public List<T> readPage(Page page) {
		int numberOfEntitiesToSkip = (page.getNumber() - 1) * page.getSize();
		
		return repository.read()
						 .stream()
						 .skip(numberOfEntitiesToSkip)
						 .limit(page.getSize())
						 .collect(Collectors.toList());
	}

}
