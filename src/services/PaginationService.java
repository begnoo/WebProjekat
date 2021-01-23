package services;

import java.util.List;
import java.util.stream.Collectors;

import core.domain.dto.Page;
import core.domain.models.BaseEntity;
import core.exceptions.BadLogicException;
import core.service.IPaginationService;

public class PaginationService<T extends BaseEntity> implements IPaginationService<T> {

	
	public PaginationService()
	{
	}
	
	@Override
	public List<T> readPage(List<T> entities, Page page) {
		if(page.getNumber() <= 0 || page.getSize() <= 0) {
			throw new BadLogicException("Pagination parameters can not be negative.");
		}
		
		if(page.getNumber() == 0 || page.getSize() == 0) {
			return entities;
		}
		
		int numberOfEntitiesToSkip = (page.getNumber() - 1) * page.getSize();
		
		return entities.stream()
					   .skip(numberOfEntitiesToSkip)
					   .limit(page.getSize())
					   .collect(Collectors.toList());
	}

}
