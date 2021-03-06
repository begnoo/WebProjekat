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
import core.domain.enums.UserRole;
import core.domain.models.User;
import core.responses.users.AuthorizedUserResponse;
import core.responses.users.WholeAdministratorObjectResponse;
import core.responses.users.WholeBuyerObjectResponse;
import core.responses.users.WholeSellerObjectResponse;
import core.responses.users.WholeUserObjectResponseBase;
import core.service.IAuthorizationService;
import core.servlets.exceptions.UnauthorizedException;
import repository.DbContext;
import servlets.utils.filters.DenyAuthorized;

@Path("authorization")
public class AuthorizationServlet extends AbstractServletBase {
	
	@Context
	ServletContext servletContext;

	private IAuthorizationService authorizationService;

	public AuthorizationServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		authorizationService = (IAuthorizationService) serviceFactory.getService(IAuthorizationService.class, context);
	}
	
	@POST
	@DenyAuthorized()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuthorizedUserResponse authorize(Credidentals credidentals) {
		super.validateRequest(credidentals);
		
		AuthorizedUser authorizedUser = authorizationService.authorize(credidentals);
		if(authorizedUser == null) {
			throw new UnauthorizedException("Combination of username and password does not match any account.");
		}
		
		return new AuthorizedUserResponse(authorizedUser.getToken(), generateUserObjectResponse(authorizedUser.getUser()));
	}
	
	private WholeUserObjectResponseBase generateUserObjectResponse(User user)
	{	
		if(user.getRole() == UserRole.Buyer) {
			return mapper.Map(new WholeBuyerObjectResponse(), user);
		} else if (user.getRole() == UserRole.Seller) {
			return mapper.Map(new WholeSellerObjectResponse(), user);
		} else {
			return mapper.Map(new WholeAdministratorObjectResponse(), user);
		}
	}
}
