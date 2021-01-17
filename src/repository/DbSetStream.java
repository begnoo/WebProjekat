package repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.domain.models.BaseEntity;
import core.repository.IComparatorFactory;
import core.repository.IDbSetStream;
import core.repository.SortingOrder;
import repository.utils.sorting.ComparatorFactory;

public class DbSetStream<T extends BaseEntity> implements IDbSetStream<T> {

	private List<T> entities;
	private List<Predicate<T>> filters;
	private List<Comparator<T>> comparators;
	private IComparatorFactory comparatorFactory;
	private Class<T> classType;
	
	public DbSetStream(List<T> entities, Class<T> classType)
	{
		this.entities = entities;
		this.classType = classType;
		this.filters = new ArrayList<Predicate<T>>();
		this.comparators = new ArrayList<Comparator<T>>();
		this.comparatorFactory = new ComparatorFactory();
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
		
		stream = stream.filter(Arrays.stream(filters.toArray(new Predicate[filters.size()])).reduce(filter -> true, Predicate::and));

		for(Comparator<T> comparator : comparators)
		{
			stream = stream.sorted(comparator);
		}		
		
		return stream.collect(Collectors.toList());
	}

	@Override
	public IDbSetStream<T> sortByAttribute(String attributeName, SortingOrder order) {
		this.comparators.add(
			new Comparator<T>() {

				@Override
				public int compare(T o1, T o2) {
					try {
						Field attributeForSorting = getFieldFromClass(attributeName);
						Method getterForAttribute = getMethodFromClass(getSetterNameForField(attributeName));
						Class<?> attributeType = attributeForSorting.getType();
						Object firstValue = getterForAttribute.invoke(o1);
						Object secondValue = getterForAttribute.invoke(o2);
						
						@SuppressWarnings("unchecked")
						Comparator<Object> comparator = (Comparator<Object>) comparatorFactory.getComparator(attributeType, order);
						
						return comparator.compare(firstValue, secondValue);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						return 0;
					}
									}
				
				private String getSetterNameForField(String fieldName)
				{
					return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				}
				
				private Field getFieldFromClass(String fieldName)
				{
					Class<?> currentClass = classType;
					do
					{
						for(Field field : currentClass.getDeclaredFields())
						{
							if(field.getName().toLowerCase().equals(fieldName.toLowerCase()))
							{
								return field;
							}
						}
					
						currentClass = currentClass.getSuperclass();
					} while(currentClass != Object.class);
					
					return null;
				}
				
				private Method getMethodFromClass(String methodName)
				{
					Class<?> currentClass = classType;
					do
					{
						for(Method method : currentClass.getDeclaredMethods())
						{
							if(method.getName().equals(methodName))
							{
								return method;
							}
						}
					
						currentClass = currentClass.getSuperclass();
					} while(currentClass != Object.class);
					
					return null;
				}
			
			}
		);
		
		return this;
	}

}
