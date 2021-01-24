package servlets.utils.filters;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.domain.models.BaseEntity;
import core.domain.models.User;
import core.service.IJwtService;
import repository.DbContext;
import repository.DbSet;
import services.JwtService;

@SuppressWarnings({ "deprecation", "unchecked" })
@Provider
public class UserSpecificEntityFilter implements ContainerRequestFilter {
    
	@Context
    private ResourceInfo resourceInfo;
		
	@Context
	ServletContext context;

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
    
    private UUID getUserIdFromRequest(ContainerRequestContext requestContext) {
		String jwtToken = requestContext.getHeaderString("Authorization");
		IJwtService jwtService = new JwtService();
		
		return jwtService.getUserIdDromJwtToken(jwtToken);
    }
    
    private String getRoleOfUser(UUID userId) {
    	DbContext dbContext = (DbContext) context.getAttribute("DbContext");
		DbSet<User> users = (DbSet<User>) dbContext.getSet(User.class);
    	User authorizedUser = users.read(userId);
    	
    	if(authorizedUser == null) {
    		return "";
    	}
    	
    	return authorizedUser.getRole().toString();
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
    
	private UUID getEntityId(ContainerRequestContext requestContext, String entityIdName) {
    	UriInfo uriInfo = requestContext.getUriInfo();
    	
    	if(uriInfo.getPathParameters().containsKey(entityIdName)) {
    		return UUID.fromString(uriInfo.getPathParameters().getFirst(entityIdName));
    	}
    	
    	if(uriInfo.getQueryParameters().containsKey(entityIdName)) {
    		return UUID.fromString(uriInfo.getQueryParameters().getFirst(entityIdName));
    	}

    	try {
            String json = IOUtils.toString(requestContext.getEntityStream(), Charsets.UTF_8);
            if(!json.isBlank())
            {
                ObjectMapper mapper = new ObjectMapper();
    			HashMap<String, String> bodyParamethers = mapper.readValue(json, HashMap.class);
                InputStream in = IOUtils.toInputStream(json);
                requestContext.setEntityStream(in);

                if(bodyParamethers.containsKey(entityIdName)) {
                	return UUID.fromString(bodyParamethers.get(entityIdName));
                }            
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    
    	return null;
    }
    
    private String getUserIdGetterName(String userRole) {
    	return "get" + userRole + "Id";
    }
}
