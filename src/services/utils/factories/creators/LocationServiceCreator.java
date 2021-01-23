package services.utils.factories.creators;

import core.domain.models.Location;
import core.repository.IRepository;
import core.service.ILocationService;
import core.service.IManifestationService;
import core.service.IServiceCreator;
import repository.DbContext;
import repository.Repository;
import services.LocationService;

public class LocationServiceCreator implements IServiceCreator<ILocationService> {

	@Override
	public ILocationService create(DbContext context) {
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		
		IManifestationService manifestationService = new ManifestationServiceCreator().create(context);

		return new LocationService(locationRepository, manifestationService);
	}


}
