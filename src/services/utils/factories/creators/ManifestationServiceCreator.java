package services.utils.factories.creators;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.service.ICommentService;
import core.service.IManifestationService;
import core.service.IServiceCreator;
import core.service.IUserTicketManifestationMediator;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import services.ManifestationService;
import services.UserTicketManifestationMediator;

public class ManifestationServiceCreator implements IServiceCreator<IManifestationService> {

	@Override
	public IManifestationService create(DbContext context) {
		IUserTicketManifestationMediator mediator = new UserTicketManifestationMediator(context);
		
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		
		ICommentService commentService = new CommentServiceCreator().create(context);
		
		return new ManifestationService(manifestationRepository, locationRepository, commentService, mediator);
	}

}
