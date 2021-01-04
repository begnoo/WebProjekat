package servlets;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.dto.AuthorizedUser;
import core.domain.dto.Credidentals;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IAuthorizationService;
import core.service.IJwtService;
import core.servlets.exceptions.UnauthorizedException;
import repository.DbContext;
import repository.UserRepository;
import services.AuthorizationService;
import services.JwtService;

@Path("auhtorization")
public class AuthorizationServlet {
	@Context
	ServletContext servletContext;

	private IAuthorizationService authorizationService;

	public AuthorizationServlet() {
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<User> userRepository = new UserRepository(context);
		IJwtService jwtService = new JwtService();
		authorizationService = new AuthorizationService(userRepository, jwtService);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuthorizedUser authorize(Credidentals credidentals) {
		AuthorizedUser authorizedUser = authorizationService.authorize(credidentals);
		if(authorizedUser == null)
		{
			throw new UnauthorizedException("Combination of username and password does not match any account.");
		}
		
		return authorizedUser;
	}
}
