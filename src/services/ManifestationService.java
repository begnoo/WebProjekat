package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.service.IManifestationService;

public class ManifestationService extends CrudService<Manifestation> implements IManifestationService {
	private IRepository<Location> locationRepository;


	public ManifestationService(IRepository<Manifestation> repository, IRepository<Location> locationRepository) {
		super(repository);
		this.locationRepository = locationRepository;
	}

	public List<Manifestation> readOrderedByDescendingDate() {
		return repository.read()
				.stream()
				.sorted((manifestation1, manifestation2) -> manifestation2.getEventDate().compareTo(manifestation1.getEventDate()))
				.collect(Collectors.toList());
	}

	@Override
	public Manifestation create(Manifestation manifestation) {
				
		if(!checkIfLocationExists(manifestation.getLocationId())) {
			return null;
		}
		
		List<Manifestation> manifestationsWithSameLocationAndOverlapingEventDate = readWithSameLocationAndOverlapingEventDate(manifestation);
		if (!manifestationsWithSameLocationAndOverlapingEventDate.isEmpty()) {
			return null;
		}
		
		manifestation.setStatus(false);
		return repository.create(manifestation);
	}

	@Override
	public Manifestation update(Manifestation manifestationForUpdate) {
		
		if(!checkIfLocationExists(manifestationForUpdate.getLocationId())) {
			return null;
		}

		List<Manifestation> manifestationsWithSameLocationAndOverlapingEventDateWithoutThisOne = readWithSameLocationAndOverlapingEventDate(manifestationForUpdate)
				.stream()
				.filter(manifestation -> manifestation.getId() != manifestationForUpdate.getId())
				.collect(Collectors.toList());
		
		if (!manifestationsWithSameLocationAndOverlapingEventDateWithoutThisOne.isEmpty()) {
			return null;
		}
		
		return repository.update(manifestationForUpdate);
	}
	
	private boolean checkIfLocationExists(UUID locationId) {
		return locationRepository.read(locationId) != null;
	}
	
	
	private List<Manifestation> readWithSameLocationAndOverlapingEventDate(Manifestation queryManifestation) {
		return repository.read()
				.stream()
				.filter(manifestation -> queryManifestation.getLocationId().equals(manifestation.getLocationId()))
				.filter(manifestation -> checkIfOverlapingManifestationDate(queryManifestation, manifestation))
				.collect(Collectors.toList());
	}
	
	private boolean checkIfOverlapingManifestationDate(Manifestation firstManifestation, Manifestation secondManifestation) {
		//TODO: srediti ovo
		LocalDateTime firstManifestationStart = firstManifestation.getEventDate();
		LocalDateTime firstManifestationEnd = firstManifestation.getEventEndDate();
	
		LocalDateTime secondManifestationStart = secondManifestation.getEventDate();
		LocalDateTime secondManifestationEnd = secondManifestation.getEventEndDate();
		
		boolean firstManifestationStartNotOverlaping = firstManifestationStart.compareTo(secondManifestationEnd) >= 0;
		boolean secondManifestationStartNotOverlaping = secondManifestationStart.compareTo(firstManifestationEnd) >= 0;
		
		return !firstManifestationStartNotOverlaping && !secondManifestationStartNotOverlaping;
	}

	@Override
	public Manifestation updateNumberOfSeats(Manifestation manifestation, int additionalSeats) {
		int newNumberOfManifestationSeats = manifestation.getSeats() + additionalSeats;
		if(newNumberOfManifestationSeats < 0) {
			return null;
		}
		
		manifestation.setSeats(newNumberOfManifestationSeats);
		
		return repository.update(manifestation);

	}

}