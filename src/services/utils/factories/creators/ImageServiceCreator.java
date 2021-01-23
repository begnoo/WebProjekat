package services.utils.factories.creators;

import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.service.IImageService;
import core.service.IServiceCreator;
import repository.DbContext;
import repository.ManifestationRepository;
import services.ImageService;

public class ImageServiceCreator implements IServiceCreator<IImageService> {

	@Override
	public IImageService create(DbContext context) {
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);

		return new ImageService(manifestationRepository);
	}

}
