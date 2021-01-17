package core.repository;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import core.domain.models.BaseEntity;

public interface IDbSetStream<T extends BaseEntity> {
	IDbSetStream<T> filter(Predicate<T> predicate);
	
	IDbSetStream<T> sort(Comparator<T> comparator);
	
	List<T> collect();
}
