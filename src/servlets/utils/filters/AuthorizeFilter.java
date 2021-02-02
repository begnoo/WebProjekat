package servlets.utils.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorizeFilter extends AbstractFilterBase {
    
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
                    .entity("{\"errorMessage\": \"You are not authorized.\"}").build());
			return;
		}
		
		Authorize authorizeAnnotation = resourceInfo.getResourceMethod().getAnnotation(Authorize.class);
		String roles = authorizeAnnotation.roles();
		String userRole = getRoleOfUser(userId);

		if(!roles.isEmpty() && !isOneOf(roles, userRole))
		{
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"errorMessage\": \"You don't have permissions for this action.\"}").build());
			return;
		}
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
