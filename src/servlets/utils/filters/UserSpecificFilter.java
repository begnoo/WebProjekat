package servlets.utils.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class UserSpecificFilter extends AbstractUserSpecificBase {
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if(!resourceInfo.getResourceMethod().isAnnotationPresent(UserSpecific.class))
    	{
    		return;
    	}
    	UserSpecific annotation = resourceInfo.getResourceMethod().getAnnotation(UserSpecific.class);

    	UUID userId = getUserIdFromRequest(requestContext);
    	String userRole = getRoleOfUser(userId);
    	if(annotation.allowTopRole() && userRole.equals("Administrator")) {
    		return;
    	}
    	
    	UUID targetedUserId = getTargetedUserId(requestContext);
		if(targetedUserId == null) {
			requestContext.abortWith(Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("{\"errorMessage\": \"User with id %s does not exist.\"}", targetedUserId)).build());
			return;
		}
		
		if(!targetedUserId.equals(userId)) {
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"errorMessage\": \"You don't have permissions for this action.\"}").build());
			return;
		}
    }
	
	private UUID getTargetedUserId(ContainerRequestContext requestContext) {
		String[] potentialUserIdNames = new String[] {
			"buyerId",
			"sellerId",
			"userId",
			"id"
		};
		
		for(String potentialUserIdName : potentialUserIdNames) {
			UUID userId = getEntityId(requestContext, potentialUserIdName);
			if(userId != null) {
				return userId;
			}
		}
		
		return null;
	}
}
