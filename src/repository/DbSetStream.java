package repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.domain.models.BaseEntity;
import core.repository.IDbSetStream;

public class DbSetStream<T extends BaseEntity> implements IDbSetStream<T> {

	private List<T> entities;
	private List<Predicate<T>> filters;
	private List<Comparator<T>> comparators;
	
	public DbSetStream(List<T> entities)
	{
		this.entities = entities;
		this.filters = new ArrayList<Predicate<T>>();
		this.comparators = new ArrayList<Comparator<T>>();
	}
	
	@Override
	public IDbSetStream<T> filter(Predicate<T> predicate) {
		this.filters.add(predicate);
		
		return this;
	}

	@Override
	public IDbSetStream<T> sort(Comparator<T> comparator) {
		this.comparators.add(comparator);
		
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> collect() {
		Stream<T> stream = entities.stream();
		
		for(Comparator<T> comparator : comparators)
		{
			stream = stream.sorted(comparator);
		}
		
		stream = stream.filter(Arrays.stream(filters.toArray(new Predicate[filters.size()])).reduce(filter -> true, Predicate::and));
		
		return stream.collect(Collectors.toList());
	}

}
