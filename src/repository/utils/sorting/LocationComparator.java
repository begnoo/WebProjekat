package repository.utils.sorting;

import java.util.Comparator;

import core.domain.models.Location;
import core.repository.SortingOrder;

public class LocationComparator implements Comparator<Location> {

	private SortingOrder order;
	
	public LocationComparator(SortingOrder order)
	{
		this.order = order;
	}
	
	@Override
	public int compare(Location o1, Location o2) {
		int placeComparison = o1.getAddress().getPlace().compareTo(o2.getAddress().getPlace());
		if(placeComparison != 0)
		{
			return placeComparison * order.getModifier();
		}
		
		int postalCodeComparison = o1.getAddress().getPostalCode().compareTo(o2.getAddress().getPostalCode());
		if(postalCodeComparison != 0)
		{
			return postalCodeComparison * order.getModifier();
		}
		
		int streetComparison = o1.getAddress().getStreet().compareTo(o2.getAddress().getStreet());
		if(streetComparison != 0)
		{
			return streetComparison * order.getModifier();
		}
		
		int houseNumberComparison = o1.getAddress().getHouseNumber().compareTo(o2.getAddress().getHouseNumber());
		
		return houseNumberComparison * order.getModifier();
	}
}
