package services;

import java.util.List;
import java.util.UUID;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.exceptions.BadLogicException;
import core.repository.IRepository;
import core.service.ILocationService;
import core.service.IManifestationService;

public class LocationService extends CrudService<Location> implements ILocationService {

	private IManifestationService manifestationService;
	
	public LocationService(IRepository<Location> repository, IManifestationService manifestationService) {
		super(repository);
		
		this.manifestationService = manifestationService;
	}
	
	@Override
	public Location delete(UUID locationId)
	{
		List<Manifestation> manifestationsOnLocation = manifestationService.readByLocationId(locationId);
		if(!manifestationsOnLocation.isEmpty()) {
			throw new BadLogicException("Can not delete location because there are manifestations on it.");
		}
		
		return super.delete(locationId);
	}

}
