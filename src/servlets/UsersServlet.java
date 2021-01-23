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
import core.domain.models.BuyerType;
import core.domain.models.Comment;
import core.domain.models.Manifestation;
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
import core.service.IBuyerTypeService;
import core.service.ICommentService;
import core.service.IPaginationService;
import core.service.IUserService;
import core.service.IUserTicketManifestationMediator;
import repository.CommentRepository;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import repository.UserRepository;
import services.BuyerTypeService;
import services.CommentService;
import services.PaginationService;
import services.UserService;
import services.UserTicketManifestationMediator;
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
		IUserTicketManifestationMediator mediator = new UserTicketManifestationMediator(context);
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);
		IRepository<Comment> commentRepository = new CommentRepository(context);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		
		IBuyerTypeService buyerTypeService = new BuyerTypeService(buyerTypeRepository);
		ICommentService commentService = new CommentService(commentRepository, manifestationRepository, userRepository);
		userService = new UserService(userRepository, buyerTypeService, commentService, mediator);
		
		paginationService = new PaginationService<User>();
		searchService = new UsersSearchService(userRepository);
	}
	
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
		
	@GET
	@Path("users/buyers/distrustful")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> readAllDistrustfulBuyers(@QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<User> users = userService.readDistrustfulBuyers();
		
		return paginationService.readPage(users, new Page(number, size));
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
	@Path("users/advance-search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> advanceSearch(UsersSearchParamethers searchParamethers, @QueryParam("number") int number, @QueryParam("size") int size) {
		super.validateRequest(searchParamethers);

		List<User> users = searchService.search(searchParamethers);

		return paginationService.readPage(users, new Page(number, size));
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
	public WholeUserObjectResponseBase delete(@PathParam("id") UUID id)
	{
		User blockedUser = userService.blockUser(id);
		
		return generateUserObjectResponse(blockedUser);
	}
		
	private WholeUserObjectResponseBase generateUserObjectResponse(User user)
	{
		if(user == null)
		{
			return null; // TODO: REMOVE
		}
		
		if(user.getRole() == UserRole.Buyer) {
			return mapper.Map(new WholeBuyerObjectResponse(), user);
		} else if (user.getRole() == UserRole.Seller) {
			return mapper.Map(new WholeSellerObjectResponse(), user);
		} else {
			return mapper.Map(new WholeAdministratorObjectResponse(), user);
		}
	}
}
