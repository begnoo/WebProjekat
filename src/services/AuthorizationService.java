package services;

import java.util.List;
import java.util.stream.Collectors;

import core.domain.dto.AuthorizedUser;
import core.domain.dto.Credidentals;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IAuthorizationService;
import core.service.IJwtService;
import services.utils.HashUtils;

public class AuthorizationService implements IAuthorizationService {
    private IRepository<User> userRepository;
    private IJwtService jwtService;

	public AuthorizationService(IRepository<User> userRepository, IJwtService jwtService) {
		super();
        this.userRepository = userRepository;
        this.jwtService = jwtService;
	}
	
    @Override
    public AuthorizedUser authorize(Credidentals credidentals) {
        List<User> users = userRepository.read()
            .stream()
            .filter(user -> user.getUsername().equals(credidentals.getUsername()))
            .filter(user -> user.getPassword().equals(HashUtils.getSaltedAndHashedPassword(credidentals.getPassword(), user.getSalt())))
            .collect(Collectors.toList());
        
        AuthorizedUser authorizedUser = null;
        if(!users.isEmpty()){
        	User userToLogin = users.get(0); 
			authorizedUser = new AuthorizedUser(userToLogin, jwtService.generateJwtTokenForUser(userToLogin.getId()));
        }
        return authorizedUser;
    }
	
}
