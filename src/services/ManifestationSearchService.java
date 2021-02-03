package services;

import java.util.List;

import core.domain.dto.ManifestationsSearchParamethers;
import core.domain.models.Manifestation;
import core.repository.IDbSetStream;
import core.repository.IRepository;
import core.repository.SortingOrder;
import core.service.IAdvanceSearchService;

public class ManifestationSearchService implements IAdvanceSearchService<Manifestation, ManifestationsSearchParamethers> {
	private IRepository<Manifestation> repository;
	
	public ManifestationSearchService(IRepository<Manifestation> repository) {
		this.repository = repository;
	}
	
	@Override
	public List<Manifestation> search(ManifestationsSearchParamethers searchParamethers) {
		IDbSetStream<Manifestation> stream = repository.getStream()
				.filter(manifestation -> manifestation.getName().toLowerCase().contains(searchParamethers.getName().toLowerCase()))
				.filter(manifestation -> searchParamethers.getDateFrom() == null || manifestation.getEventDate().compareTo(searchParamethers.getDateFrom()) >= 0)
				.filter(manifestation -> searchParamethers.getDateTo() == null || manifestation.getEventDate().compareTo(searchParamethers.getDateTo()) <= 0)
				.filter(manifestation -> manifestation.getLocation().getAddress().getPlace().toLowerCase().contains(searchParamethers.getCity().toLowerCase()))
				.filter(manifestation -> manifestation.getRegularTicketPrice() >= searchParamethers.getPriceFrom())
				.filter(manifestation -> manifestation.getRegularTicketPrice() <= searchParamethers.getPriceTo())
				.filter(manifestation -> searchParamethers.getType().isBlank() || manifestation.getType().toString().equals(searchParamethers.getType()))
				.filter(manifestation -> !searchParamethers.isOnlyNotSolved() || manifestation.getSeats() != 0)
				.filter(manifestation -> searchParamethers.getStatusSetting().equals("All") || searchParamethers.isStatus() == manifestation.isStatus())
				.filter(manifestation -> searchParamethers.getSellerId() == null || manifestation.getSellerId().equals(searchParamethers.getSellerId()));
		if(!searchParamethers.getSortBy().isBlank())
		{
			stream = stream.sortByAttribute(searchParamethers.getSortBy(), SortingOrder.valueOf(searchParamethers.getOrderBy()));
		}
		
		return stream.collect();
	}
}
