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
import core.service.IUserService;
import core.servlets.IMapper;
import repository.DbContext;
import repository.UserRepository;
import servlets.utils.mapper.ObjectMapper;

@Path("users")
public class UsersServlets {

	@Context
	ServletContext servletContext;
	
	private IUserService userService;
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
	public User readById(@PathParam("id") UUID id)
	{
		return userRepository.read(id);
	}
	
	@POST
	@Path("/buyer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User createBuyer(CreateBuyerRequest request)
	{
		User user = mapper.Map(Buyer.class, request);
		user.setRole(UserRole.Buyer);
		
		return userRepository.create(user);
	}
	
	@POST
	@Path("/seller")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User createBuyer(CreateSellerRequest request)
	{
		User user = mapper.Map(Seller.class, request);
		user.setRole(UserRole.Seller);
		
		return userRepository.create(user);
	}
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return userRepository.delete(id);
	}
	
}
