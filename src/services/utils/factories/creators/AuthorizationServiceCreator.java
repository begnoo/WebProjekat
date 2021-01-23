package services.utils.factories.creators;

import core.domain.models.User;
import core.repository.IRepository;
import core.service.IAuthorizationService;
import core.service.IJwtService;
import core.service.IServiceCreator;
import repository.DbContext;
import repository.UserRepository;
import services.AuthorizationService;

public class AuthorizationServiceCreator implements IServiceCreator<IAuthorizationService> {

	@Override
	public IAuthorizationService create(DbContext context) {
		IRepository<User> userRepository = new UserRepository(context);
		
		IJwtService jwtService = new JwtServiceCreator().create(context);
		
		return new AuthorizationService(userRepository, jwtService);
	}

}
