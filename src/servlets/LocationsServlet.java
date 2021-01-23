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
import core.domain.models.Comment;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.User;
import core.repository.IRepository;
import core.requests.locations.CreateLocationRequest;
import core.requests.locations.UpdateLocationRequest;
import core.service.ICommentService;
import core.service.ICrudService;
import core.service.IManifestationService;
import core.service.IPaginationService;
import core.service.IUserTicketManifestationMediator;
import repository.CommentRepository;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import repository.UserRepository;
import services.CommentService;
import services.LocationService;
import services.ManifestationService;
import services.PaginationService;
import services.UserTicketManifestationMediator;

@Path("locations")
public class LocationsServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private ICrudService<Location> locationService;
	private IPaginationService<Location> paginationService;

	public LocationsServlet()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IUserTicketManifestationMediator mediator = new UserTicketManifestationMediator(context);
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<Comment> commentRepository = new CommentRepository(context);
		IRepository<User> userRepository = new UserRepository(context);
		ICommentService commentService = new CommentService(commentRepository, manifestationRepository, userRepository);
		IManifestationService manifestationService = new ManifestationService(manifestationRepository, locationRepository, commentService, mediator);
		locationService = new LocationService(locationRepository, manifestationService);
		paginationService = new PaginationService<Location>();	
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> readAll(@QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<Location> locations = locationService.read();
		
		return paginationService.readPage(locations, new Page(number, size));
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Location readById(@PathParam("id") UUID id)
	{
		return locationService.read(id);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location create(CreateLocationRequest request)
	{
		super.validateRequest(request);

		Location location = mapper.Map(new Location(), request);
		
		return locationService.create(location);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location update(UpdateLocationRequest request)
	{
		super.validateRequest(request);

		Location location = locationService.read(request.getId());
		if(location == null) {
			return null;
		}
		Location LocationForUpdate = mapper.Map(location, request);
		
		return locationService.update(LocationForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return locationService.delete(id);
	}

}
