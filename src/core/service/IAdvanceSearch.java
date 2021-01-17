package core.service;

import java.util.List;

public interface IAdvanceSearch<T, R> {
	List<T> search(R searchParamethers);
}
