package core.service;

import java.util.List;

import core.domain.dto.Page;
import core.domain.models.BaseEntity;

public interface IPaginationService<T extends BaseEntity> {
	List<T> readPage(List<T> entities, Page page);
}
