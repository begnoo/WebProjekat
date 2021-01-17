package repository.utils.sorting;

import java.util.Comparator;

import core.repository.SortingOrder;

public class IntComparator implements Comparator<Integer> {

	private SortingOrder order;
	
	public IntComparator(SortingOrder order)
	{
		this.order = order;
	}
	
	@Override
	public int compare(Integer o1, Integer o2) {
		return (o1 - o2) * order.getModifier();
	}

}
