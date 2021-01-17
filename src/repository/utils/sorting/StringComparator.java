package repository.utils.sorting;

import java.util.Comparator;

import core.repository.SortingOrder;

public class StringComparator implements Comparator<String> {
	private SortingOrder order;
	
	public StringComparator(SortingOrder order)
	{
		this.order = order;
	}
	
	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2) * order.getModifier();
	}

}
