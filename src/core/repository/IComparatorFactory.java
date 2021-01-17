package core.repository;

import java.util.Comparator;

public interface IComparatorFactory {
	Comparator<?> getComparator(Class<?> attributeType, SortingOrder order);

}
