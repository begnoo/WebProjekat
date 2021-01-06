package core.service;

import java.util.List;

import core.domain.models.Manifestation;

public interface IManifestationService extends ICrudService<Manifestation> {
	List<Manifestation> readOrderedByDescendingDate();
	
	Manifestation updateNumberOfSeats(Manifestation manifestation, int additionalSeats);
}
