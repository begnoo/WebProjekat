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
import core.domain.models.Location;
import core.repository.IRepository;
import core.requests.locations.CreateLocationRequest;
import core.requests.locations.UpdateLocationRequest;
import core.service.ICrudService;
import core.service.IPaginationService;
import repository.DbContext;
import repository.Repository;
import services.CrudService;
import services.PaginationService;

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
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		locationService = new CrudService<Location>(locationRepository);
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
