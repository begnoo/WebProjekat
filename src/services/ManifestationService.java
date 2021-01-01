package services;

import java.util.List;
import java.util.stream.Collectors;

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
        .sorted((manifestation1, manifestation2) ->
            manifestation1.getEventDate().compareTo(manifestation2.getEventDate()))
        .collect(Collectors.toList());
    }


}