package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.service.IManifestationService;

public class ManifestationService extends CrudService<Manifestation> implements IManifestationService {

	public ManifestationService(IRepository<Manifestation> repository) {
		super(repository);
	}

	public List<Manifestation> readOrderdByDescendingDate() {
		return repository.read()
				.stream()
				.sorted((manifestation1, manifestation2) -> manifestation1.getEventDate()
				.compareTo(manifestation2.getEventDate())).collect(Collectors.toList());
	}

	@Override
	public Manifestation create(Manifestation entity) {
		List<Manifestation> manifestations = readByLocationAndEventDate(entity.getLocation(), entity.getEventDate());
		if (!manifestations.isEmpty()) {
			return null;
		}
		entity.setStatus(false);
		return repository.create(entity);
	}

	@Override
	public Manifestation update(Manifestation entityForUpdate) {
		//TODO: razmotriti kako update uz request
		List<Manifestation> manifestations = readByLocationAndEventDate(entityForUpdate.getLocation(), entityForUpdate.getEventDate());
		if (!manifestations.isEmpty()) {
			return null;
		}
		return repository.update(entityForUpdate);
	}

	private List<Manifestation> readByLocationAndEventDate(Location location, LocalDateTime eventDate) {
		return repository.read()
				.stream()
				.filter(manifestation -> location.getId().equals(manifestation.getId()))
				.filter(manifestation -> eventDate.compareTo(manifestation.getEventDate()) == 0)
				.collect(Collectors.toList());
	}

}