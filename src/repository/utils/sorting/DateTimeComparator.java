package repository.utils.sorting;

import java.time.LocalDateTime;
import java.util.Comparator;

import core.repository.SortingOrder;

public class DateTimeComparator implements Comparator<LocalDateTime> {
	private SortingOrder order;
	
	public DateTimeComparator(SortingOrder order)
	{
		this.order = order;
	}

	@Override
	public int compare(LocalDateTime o1, LocalDateTime o2) {
		return o1.compareTo(o2) * order.getModifier();
	}
}
