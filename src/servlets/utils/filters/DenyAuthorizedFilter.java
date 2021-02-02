package servlets.utils.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
public class DenyAuthorizedFilter extends AbstractFilterBase {
    		
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
                    .entity("{\"errorMessage\": \"You don't have permissions for this action.\"}").build());
			return;
		}
    }
}
