package services;

import java.util.List;
import java.util.UUID;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.service.IManifestationService;

public class LocationService extends CrudService<Location> {

	private IManifestationService manifestationService;
	
	public LocationService(IRepository<Location> repository, IManifestationService manifestationService) {
		super(repository);
		
		this.manifestationService = manifestationService;
	}
	
	@Override
	public boolean delete(UUID locationId)
	{
		List<Manifestation> manifestationsOnLocation = manifestationService.readByLocationId(locationId);
		if(!manifestationsOnLocation.isEmpty()) {
			return false;
		}
		
		return super.delete(locationId);
	}

}
