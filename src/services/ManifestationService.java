package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IManifestationService;

public class ManifestationService extends CrudService<Manifestation> implements IManifestationService {
	private IRepository<Location> locationRepository;
	private IRepository<User> userRepository;


	public ManifestationService(IRepository<Manifestation> repository, IRepository<Location> locationRepository,
			IRepository<User> userRepository) {
		super(repository);
		this.locationRepository = locationRepository;
		this.userRepository = userRepository;
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
		
		List<Manifestation> manifestationsOnLocationAtEventDate = readByLocationAndEventDate(manifestation.getLocationId(), manifestation.getEventDate());
		if (!manifestationsOnLocationAtEventDate.isEmpty()) {
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
		
		List<Manifestation> manifestationsOnLocationAtEventDateWithoutThisOne = readByLocationAndEventDate(manifestationForUpdate.getLocationId(), manifestationForUpdate.getEventDate())
				.stream()
				.filter(manifestation -> manifestation.getId() != manifestationForUpdate.getId())
				.collect(Collectors.toList());
		
		if (!manifestationsOnLocationAtEventDateWithoutThisOne.isEmpty()) {
			return null;
		}
		
		return repository.update(manifestationForUpdate);
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