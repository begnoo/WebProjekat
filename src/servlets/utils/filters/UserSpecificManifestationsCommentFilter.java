package servlets.utils.filters;

import java.io.IOException;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import core.domain.models.Comment;
import repository.DbContext;
import repository.DbSet;

@Provider
public class UserSpecificManifestationsCommentFilter extends AbstractUserSpecificBase {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if(!resourceInfo.getResourceMethod().isAnnotationPresent(UserSpecificManifestationsComment.class))
    	{
    		return;
    	}
		
		UUID sellerId = getUserIdFromRequest(requestContext);
    	UUID commentId = getEntityId(requestContext, "commentId");
    	Comment comment = getComment(commentId);
    	
		if(comment == null) {
			requestContext.abortWith(Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Comment with id %s does not exist.", commentId)).build());
			return;
		}
		
		if(!comment.getManifestation().getSellerId().equals(sellerId)) {
			requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("You don't have permissions for this action.").build());
			return;
		}
	}

	protected Comment getComment(UUID commentId)
    {
    	DbContext dbContext = (DbContext) context.getAttribute("DbContext");
    	@SuppressWarnings("unchecked")
		DbSet<Comment> comments = (DbSet<Comment>) dbContext.getSet(Comment.class);
    	
    	return comments.read(commentId);
    }
}
