package repository.utils.sorting;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.repository.IComparatorFactory;
import core.repository.SortingOrder;

public class ComparatorFactory implements IComparatorFactory {

	private HashMap<Class<?>, Class<?>> comparatorClasses;

	public ComparatorFactory()
	{
		this.comparatorClasses = new HashMap<Class<?>, Class<?>>();
		this.comparatorClasses.put(int.class, IntComparator.class);
		this.comparatorClasses.put(String.class, StringComparator.class);
		this.comparatorClasses.put(LocalDateTime.class, DateTimeComparator.class);
		this.comparatorClasses.put(Location.class, LocationComparator.class);
		this.comparatorClasses.put(Manifestation.class, ManifestationComparator.class);
	}
	
	
	@Override
	public Comparator<?> getComparator(Class<?> attributeType, SortingOrder order) {
		Class<?> classOfComparator = comparatorClasses.get(attributeType);
		
		Comparator<?> comparatorObject = null;
		try {
			comparatorObject = (Comparator<?>) classOfComparator.getConstructor(order.getClass())
																.newInstance(order);
		} catch (Exception e) {
			System.out.println("Error while trying to instantiate comparator for: " + attributeType.getSimpleName());
		} 
		
		return comparatorObject;
	}

}
