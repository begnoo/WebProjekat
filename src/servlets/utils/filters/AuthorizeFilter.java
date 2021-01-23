package servlets.utils.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import core.domain.models.User;
import core.service.IJwtService;
import repository.DbContext;
import repository.DbSet;
import services.JwtService;

@Provider
public class AuthorizeFilter implements ContainerRequestFilter {
    
	@Context
    private ResourceInfo resourceInfo;
	
	@Context
	private ServletContext context;
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if(!resourceInfo.getResourceMethod().isAnnotationPresent(Authorize.class))
    	{
    		return;
    	}
    	
		UUID userId = getUserIdFromRequest(requestContext);
		if(userId == null)
		{
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("You are not authorized.").build());
			return;
		}
		
		Authorize authorizeAnnotation = resourceInfo.getResourceMethod().getAnnotation(Authorize.class);
		String roles = authorizeAnnotation.roles();
		String userRole = getRoleOfUser(userId);

		if(!roles.isEmpty() && !isOneOf(roles, userRole))
		{
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("You don't have permissions for this action.").build());
			return;
		}
    }
    
    private UUID getUserIdFromRequest(ContainerRequestContext ctx)
    {
		String jwtToken = ctx.getHeaderString("Authorization");
		IJwtService jwtService = new JwtService();
		
		return jwtService.getUserIdDromJwtToken(jwtToken);
    }
    
    private String getRoleOfUser(UUID userId)
    {
    	DbContext dbContext = (DbContext) context.getAttribute("DbContext");
    	@SuppressWarnings("unchecked")
		DbSet<User> users = (DbSet<User>) dbContext.getSet(User.class);
    	User authorizedUser = users.read(userId);
    	
    	return authorizedUser.getRole().toString();
    }
    
    private boolean isOneOf(String joinedRoles, String userRole)
    {
    	String[] roles = joinedRoles.split(",");
    	for(String role : roles) {
    		if(userRole.equals(role.trim())) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
