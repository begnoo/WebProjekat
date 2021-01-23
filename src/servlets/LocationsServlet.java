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
import core.requests.locations.CreateLocationRequest;
import core.requests.locations.UpdateLocationRequest;
import core.service.ILocationService;
import core.service.IPaginationService;
import core.servlets.exceptions.NotFoundException;
import repository.DbContext;
import services.PaginationService;

@Path("locations")
public class LocationsServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private ILocationService locationService;
	private IPaginationService<Location> paginationService;

	public LocationsServlet()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		locationService = (ILocationService) serviceFactory.getService(ILocationService.class, context);
		
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
		Location location = locationService.read(id);
		if(location == null) {
			throw new NotFoundException("Location does not exists.");
		}

		return location;
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
			throw new NotFoundException("Location does not exists.");
		}
		Location LocationForUpdate = mapper.Map(location, request);
		
		return locationService.update(LocationForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Location delete(@PathParam("id") UUID id)
	{
		return locationService.delete(id);
	}

}
