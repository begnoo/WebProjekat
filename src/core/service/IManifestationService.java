package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.models.Manifestation;

public interface IManifestationService extends ICrudService<Manifestation> {
	List<Manifestation> readOrderedByDescendingDate();
	
	List<Manifestation> readByLocationId(UUID locationId);
	
	Manifestation updateNumberOfSeats(Manifestation manifestation, int additionalSeats);
	
	int getRating(UUID manifestationId);
}
