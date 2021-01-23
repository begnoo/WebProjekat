package services.utils.factories.creators;

import core.domain.models.User;
import core.repository.IRepository;
import core.service.IBuyerTypeService;
import core.service.ICommentService;
import core.service.IServiceCreator;
import core.service.IUserService;
import core.service.IUserTicketManifestationMediator;
import repository.DbContext;
import repository.UserRepository;
import services.UserService;
import services.UserTicketManifestationMediator;

public class UserServiceCreator implements IServiceCreator<IUserService> {

	@Override
	public IUserService create(DbContext context) {
		IUserTicketManifestationMediator mediator = new UserTicketManifestationMediator(context);
		
		IRepository<User> userRepository = new UserRepository(context);
		
		IBuyerTypeService buyerTypeService = new BuyerTypeServiceCreator().create(context);
		ICommentService commentService = new CommentServiceCreator().create(context);
		
		return new UserService(userRepository, buyerTypeService, commentService, mediator);
	}

}
