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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.models.Address;
import core.domain.models.Location;
import core.repository.IRepository;
import core.requests.locations.CreateAddressRequest;
import core.requests.locations.CreateLocationRequest;
import core.requests.locations.UpdateAddressRequest;
import core.requests.locations.UpdateLocationRequest;
import core.servlets.IMapper;
import repository.DbContext;
import repository.Repository;
import servlets.utils.mapper.ObjectMapper;

@Path("locations")
public class LocationsServlet {
	@Context
	ServletContext servletContext;

	private IRepository<Location> locationRepository;
	private IMapper mapper;
	
	public LocationsServlet()
	{
		mapper = new ObjectMapper();
		mapper.addNestedMapping(CreateAddressRequest.class, Address.class);
		mapper.addNestedMapping(UpdateAddressRequest.class, Address.class);
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		locationRepository = new Repository<Location>(context, Location.class);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> readAll()
	{
		return locationRepository.read();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Location readById(@PathParam("id") UUID id)
	{
		return locationRepository.read(id);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location create(CreateLocationRequest request)
	{
		Location location = mapper.Map(new Location(), request);
		
		return locationRepository.create(location);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Location update(UpdateLocationRequest request)
	{
		Location LocationForUpdate = mapper.Map(locationRepository.read(request.getId()), request);
		
		return locationRepository.update(LocationForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return locationRepository.delete(id);
	}

}