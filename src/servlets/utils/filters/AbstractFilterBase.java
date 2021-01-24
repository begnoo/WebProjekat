package servlets.utils.filters;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

import core.domain.models.User;
import core.service.IJwtService;
import repository.DbContext;
import repository.DbSet;
import services.JwtService;

public abstract class AbstractFilterBase implements ContainerRequestFilter {

	@Context
    protected ResourceInfo resourceInfo;
	
	@Context
	protected ServletContext context;

	protected UUID getUserIdFromRequest(ContainerRequestContext ctx)
    {
		String jwtToken = ctx.getHeaderString("Authorization");
		IJwtService jwtService = new JwtService();
		
		return jwtService.getUserIdDromJwtToken(jwtToken);
    }
    
	protected String getRoleOfUser(UUID userId)
    {
    	DbContext dbContext = (DbContext) context.getAttribute("DbContext");
    	@SuppressWarnings("unchecked")
		DbSet<User> users = (DbSet<User>) dbContext.getSet(User.class);
    	User authorizedUser = users.read(userId);
    	
    	return authorizedUser.getRole().toString();
    }
}
