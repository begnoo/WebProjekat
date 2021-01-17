package core.service;

import java.util.List;

public interface IAdvanceSearchService<T, R> {
	List<T> search(R searchParamethers);
}
