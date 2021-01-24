package servlets;


import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.dto.Page;
import core.domain.dto.UsersSearchParamethers;
import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IRepository;
import core.requests.users.ChangePasswordRequest;
import core.requests.users.CreateBuyerRequest;
import core.requests.users.CreateSellerRequest;
import core.requests.users.UpdateUserRequest;
import core.responses.users.WholeAdministratorObjectResponse;
import core.responses.users.WholeBuyerObjectResponse;
import core.responses.users.WholeSellerObjectResponse;
import core.responses.users.WholeUserObjectResponseBase;
import core.service.IAdvanceSearchService;
import core.service.IPaginationService;
import core.service.IUserService;
import repository.DbContext;
import repository.UserRepository;
import services.PaginationService;
import services.UsersSearchService;

@Path("/")
public class UsersServlet extends AbstractServletBase {

	@Context
	ServletContext servletContext;
	
	private IUserService userService;
	private IAdvanceSearchService<User, UsersSearchParamethers> searchService;
	private IPaginationService<User> paginationService;

	public UsersServlet()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		userService = (IUserService) serviceFactory.getService(IUserService.class, context);
				
		paginationService = new PaginationService<User>();
		
		IRepository<User> userRepository = new UserRepository(context);
		searchService = new UsersSearchService(userRepository);
	}
	
	// TODO: ADMINISTRATOR
	@GET
	@Path("users/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAll(@QueryParam("role") UserRole role, @QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<User> users = null;
		if(role != null) {
			users = userService.readByUserRole(role);
		} else {
			users = userService.read();
		}
		
		return paginationService.readPage(users, new Page(number, size));
	}
	
	// TODO: ADMINISTRATOR
	@GET
	@Path("users/buyers/distrustful")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAllDistrustfulBuyers(@QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<User> users = userService.readDistrustfulBuyers();
		
		return paginationService.readPage(users, new Page(number, size));
	}
	
	// TODO: AUTENTIFIKOVAN
	@GET
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase readById(@PathParam("id") UUID id)
	{
		User user = userService.read(id);
		if(user == null) {
			throw new NotFoundException("User does not exists.");
		}
				
		return generateUserObjectResponse(user);
	}
	
	// TODO: ADMINISTRATOR
	@POST
	@Path("users/advance-search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> advanceSearch(UsersSearchParamethers searchParamethers, @QueryParam("number") int number, @QueryParam("size") int size) {
		super.validateRequest(searchParamethers);

		List<User> users = searchService.search(searchParamethers);

		return paginationService.readPage(users, new Page(number, size));
	}
	
	// TODO: NOT AUTHORIZED (MOZDA KAO ANOTACIJA NOVA)
	@POST
	@Path("users/buyer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase createBuyer(CreateBuyerRequest request)
	{
		super.validateRequest(request);
		
		User user = mapper.Map(new Buyer(), request);
		
		User createdUser = userService.create(user);
		
		return generateUserObjectResponse(createdUser);
	}
	
	// TODO: ADMINISTRATOR
	@POST
	@Path("users/seller")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase createSeller(CreateSellerRequest request)
	{
		super.validateRequest(request);

		User user = mapper.Map(new Seller(), request);
		
		User createdUser = userService.create(user);

		return generateUserObjectResponse(createdUser);
	}
	
	// TODO: AUTENTIFIKOVAN
	// TODO: PROFILE SPECIFIC
	@PUT
	@Path("users/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase update(UpdateUserRequest request)
	{
		super.validateRequest(request);
		User user = userService.read(request.getId());
		if(user == null) {
			throw new NotFoundException("User does not exists.");
		}
		
		User userForUpdate = mapper.Map(user, request);

		User updatedUser = userService.update(userForUpdate);
		
		return generateUserObjectResponse(updatedUser);
	}
	
	// TODO: AUTENTIFIKOVAN
	// TODO: PROFILE SPECIFIC
	@PUT
	@Path("users/{id}/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase changePassword(@PathParam("id") UUID id, ChangePasswordRequest request)
	{
		super.validateRequest(request);
		
		User updatedUser = userService.changePassword(id, request.getNewPassword(), request.getCurrentPassword());
		
		return generateUserObjectResponse(updatedUser);
	}
	
	// TODO: ADMINISTRATOR
	@DELETE
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase delete(@PathParam("id") UUID id)
	{
		User blockedUser = userService.blockUser(id);
		
		return generateUserObjectResponse(blockedUser);
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
