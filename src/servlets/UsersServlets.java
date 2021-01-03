package servlets;


import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IRepository;
import core.requests.users.CreateBuyerRequest;
import core.requests.users.CreateSellerRequest;
import core.responses.users.WholeAdministratorObjectResponse;
import core.responses.users.WholeBuyerObjectResponse;
import core.responses.users.WholeSellerObjectResponse;
import core.responses.users.WholeUserObjectResponseBase;
import core.servlets.IMapper;
import repository.DbContext;
import repository.UserRepository;
import servlets.utils.mapper.ObjectMapper;

@Path("users")
public class UsersServlets {

	@Context
	ServletContext servletContext;
	
	//private IUserService userService;
	private IRepository<User> userRepository; // TODO: remove later and use service
	private IMapper mapper;
	
	public UsersServlets()
	{
		mapper = new ObjectMapper();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		userRepository = new UserRepository(context);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAll()
	{
		return userRepository.read();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase readById(@PathParam("id") UUID id)
	{
		User user = userRepository.read(id);
		
		return generateUserObjectResponse(user);
	}
	
	@POST
	@Path("/buyer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase createBuyer(CreateBuyerRequest request)
	{
		User user = mapper.Map(new Buyer(), request);
		user.setRole(UserRole.Buyer);
		
		User createdUser = userRepository.create(user);
		
		return generateUserObjectResponse(createdUser);
	}
	
	@POST
	@Path("/seller")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeUserObjectResponseBase createBuyer(CreateSellerRequest request)
	{
		User user = mapper.Map(new Seller(), request);
		user.setRole(UserRole.Seller);
		
		User createdUser = userRepository.create(user);

		return generateUserObjectResponse(createdUser);
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return userRepository.delete(id);
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
