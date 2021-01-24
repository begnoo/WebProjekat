package servlets.utils.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import core.service.IJwtService;
import services.JwtService;


@Provider
public class DenyAuthorizedFilter implements ContainerRequestFilter {
    
	@Context
    private ResourceInfo resourceInfo;
		
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if(!resourceInfo.getResourceMethod().isAnnotationPresent(DenyAuthorized.class))
    	{
    		return;
    	}
    	
		UUID userId = getUserIdFromRequest(requestContext);
		if(userId != null)
		{
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("You don't have permissions for this action").build());
			return;
		}
    }
    
    private UUID getUserIdFromRequest(ContainerRequestContext ctx)
    {
		String jwtToken = ctx.getHeaderString("Authorization");
		IJwtService jwtService = new JwtService();
		
		return jwtService.getUserIdDromJwtToken(jwtToken);
    }
}
