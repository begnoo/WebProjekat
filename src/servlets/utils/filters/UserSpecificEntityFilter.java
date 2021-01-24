package servlets.utils.filters;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import core.domain.models.BaseEntity;
import repository.DbContext;
import repository.DbSet;

@Provider
public class UserSpecificEntityFilter extends AbstractUserSpecificBase {
  
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	if(!resourceInfo.getResourceMethod().isAnnotationPresent(UserSpecificEntity.class))
    	{
    		return;
    	}
    	UserSpecificEntity annotation = resourceInfo.getResourceMethod().getAnnotation(UserSpecificEntity.class);

    	UUID userId = getUserIdFromRequest(requestContext);
    	String userRole = getRoleOfUser(userId);
    	if(annotation.allowTopRole() && userRole.equals("Administrator")) {
    		return;
    	}
    	
    	String entityClassName = annotation.what();
    	Class<?> entityClass = getEntityClass(entityClassName);
    	String entityIdName = getEntityIdName(entityClassName);
    	UUID entityId = getEntityId(requestContext, entityIdName);
    	if(entityId == null) {
    		entityId = getEntityId(requestContext, "id");
    	}
    	
    	DbContext dbContext = (DbContext) context.getAttribute("DbContext");
		DbSet<?> entities = (DbSet<?>) dbContext.getSet(entityClass);
		BaseEntity entity = entities.read(entityId);
		
		if(entity == null) {
			requestContext.abortWith(Response.status(Response.Status.NOT_FOUND)
                    .entity(String.format("Entity with id %s does not exist.", entityId)).build());
			return;
		}
		
		try {
			Method getterForUserIdFromEntity = entityClass.getDeclaredMethod(getUserIdGetterName(annotation.belongsTo()));
			UUID userIdFromEntity = (UUID) getterForUserIdFromEntity.invoke(entity);
			if(!userIdFromEntity.equals(userId)) {
				requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
	                    .entity("You don't have permissions for this action.").build());
				return;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
      
    private Class<?> getEntityClass(String entityClassName) {
    	try {
			return Class.forName("core.domain.models." + entityClassName);
		} catch (ClassNotFoundException e) {
			System.out.println("Couldn't find class: " + entityClassName);
		}
    	
    	return null;
    }
    
    private String getEntityIdName(String entityClassName) {
    	return Character.toLowerCase(entityClassName.charAt(0)) + entityClassName.substring(1) + "Id";
    }
    
    private String getUserIdGetterName(String userRole) {
    	return "get" + userRole + "Id";
    }
}
