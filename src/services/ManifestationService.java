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
	
	IRepository<Location> locationRepository;

	public ManifestationService(IRepository<Manifestation> repository, IRepository<Location> locationRepository) {
		super(repository);
		this.locationRepository = locationRepository;
	}

	public List<Manifestation> readOrderdByDescendingDate() {
		return repository.read()
				.stream()
				.sorted(
				(manifestation1, manifestation2) -> manifestation2.getEventDate().compareTo(manifestation1.getEventDate()))
				.collect(Collectors.toList());
	}

	@Override
	public Manifestation create(Manifestation entity) {
		
		if(!checkIfLocationExists(entity.getLocationId())) {
			return null;
		}
		
		List<Manifestation> manifestations = readByLocationAndEventDate(entity.getLocationId(), entity.getEventDate());
		
		if (!manifestations.isEmpty()) {
			return null;
		}
		
		entity.setStatus(false);
		return repository.create(entity);
	}

	@Override
	public Manifestation update(Manifestation entityForUpdate) {
		
		if(!checkIfLocationExists(entityForUpdate.getLocationId())) {
			return null;
		}
		
		List<Manifestation> manifestations = readByLocationAndEventDate(entityForUpdate.getLocationId(), entityForUpdate.getEventDate())
				.stream()
				.filter(manifestation -> manifestation.getId() != entityForUpdate.getId())
				.collect(Collectors.toList());
		
		if (!manifestations.isEmpty()) {
			return null;
		}
		
		return repository.update(entityForUpdate);
	}
	
	private boolean checkIfLocationExists(UUID locationId) {
		return locationRepository.read(locationId) != null;
	}

	private List<Manifestation> readByLocationAndEventDate(UUID locationId, LocalDateTime eventDate) {
		return repository.read()
				.stream()
				.filter(manifestation -> locationId.equals(manifestation.getLocationId()))
				.filter(manifestation -> eventDate.compareTo(manifestation.getEventDate()) == 0)
				.collect(Collectors.toList());
	}

}