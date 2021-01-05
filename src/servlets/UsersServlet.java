package servlets;


import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.dto.Page;
import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
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
import core.service.IBuyerTypeService;
import core.service.IPaginationService;
import core.service.IUserService;
import core.servlets.exceptions.NotFoundException;
import repository.DbContext;
import repository.Repository;
import repository.UserRepository;
import services.BuyerTypeService;
import services.PaginationService;
import services.UserService;

@Path("/")
public class UsersServlet extends AbstractServletBase {

	@Context
	ServletContext servletContext;
	
	private IUserService userService;
	private IPaginationService<User> paginationService;
	
	public UsersServlet()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);
		
		IBuyerTypeService buyerTypeService = new BuyerTypeService(buyerTypeRepository);
		
		userService = new UserService(userRepository, buyerTypeService);
		
		paginationService = new PaginationService<User>(userRepository);
	}
	
	@GET
	@Path("users/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAll(@QueryParam("role") UserRole role)
	{
		if(role != null) {
			return userService.readByUserRole(role);
		}
		
		return userService.read();
	}
	
	@GET
	@Path("users/page")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAll(@QueryParam("number") int number, @QueryParam("size") int size)
	{		
		return paginationService.readPage(new Page(number, size));
	}
	
	@GET
	@Path("users/buyers/distrustful")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAllDistrustfulBuyers()
	{
		return userService.readDistrustfulBuyers();
	}
	
	@GET
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase readById(@PathParam("id") UUID id)
	{
		User user = userService.read(id);
		if(user == null)
		{
			throw new NotFoundException("User not found.");
		}
				
		return generateUserObjectResponse(user);
	}
	
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
	
	@PUT
	@Path("users/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase update(UpdateUserRequest request)
	{
		super.validateRequest(request);

		User user = userService.read(request.getId());
		if(user == null) {
			return null;
		}
		
		User userForUpdate = mapper.Map(user, request);

		User updatedUser = userService.update(userForUpdate);
		
		return generateUserObjectResponse(updatedUser);
	}
	
	@PUT
	@Path("users/{id}/password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase changePassword(@PathParam("id") UUID id, ChangePasswordRequest request)
	{
		super.validateRequest(request);
		
		User updatedUser = userService.changePassword(id, request.getNewPassword(), request.getCurrentPassword());
		
		if(updatedUser == null) {
			return null;
		}
		
		return generateUserObjectResponse(updatedUser);
	}
	
	@DELETE
	@Path("users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return userService.blockUser(id) != null;
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
