package repository.utils.sorting;

import java.util.Comparator;

import core.domain.models.Manifestation;
import core.repository.SortingOrder;

public class ManifestationComparator implements Comparator<Manifestation> {
	private SortingOrder order;
	
	public ManifestationComparator(SortingOrder order)
	{
		this.order = order;
	}

	@Override
	public int compare(Manifestation o1, Manifestation o2) {
		return o1.getName().compareTo(o2.getName()) * order.getModifier();
	}
}
