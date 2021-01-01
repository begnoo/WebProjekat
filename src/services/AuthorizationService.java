package services;

import java.util.List;
import java.util.stream.Collectors;

import core.domain.dto.AuthorizedUser;
import core.domain.dto.Credidentals;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IAuthorizationService;

public class AuthorizationService implements IAuthorizationService {
	
    private IRepository<User> userRepository;

	public AuthorizationService(IRepository<User> userRepository) {
		super();
        this.userRepository = userRepository;
	}
	
    @Override
    public AuthorizedUser authorize(Credidentals credidentals) {
        List<User> users = userRepository.read()
            .stream()
            .filter(user -> user.getUsername().equals(credidentals.getUsername()))
            .filter(user -> user.getPassword().equals(credidentals.getPassword()))
            .collect(Collectors.toList());
        
        AuthorizedUser userToLogin = null;
        if(!users.isEmpty()){
            userToLogin = new AuthorizedUser(users.get(0), "");
        }
        return userToLogin;
    }
	
}
