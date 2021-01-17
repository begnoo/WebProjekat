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

import core.domain.dto.ManifestationsSearchParamethers;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.requests.manifestations.CreateManifestationRequest;
import core.requests.manifestations.UpdateManifestationRequest;
import core.responses.manifestations.WholeManifestationObjectResponse;
import core.service.IAdvanceSearchService;
import core.service.IManifestationService;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import services.ManifestationSearchService;
import services.ManifestationService;

@Path("/")
public class ManifestationServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private IManifestationService manifestationService;
	private IAdvanceSearchService<Manifestation, ManifestationsSearchParamethers> searchService;
	public ManifestationServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		manifestationService = new ManifestationService(manifestationRepository, locationRepository);
		searchService = new ManifestationSearchService(manifestationRepository);
	}

	@GET
	@Path("manifestations/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manifestation> readAll(@QueryParam("order-by-date") boolean orderByDate) {
		if(orderByDate) {
			return manifestationService.readOrderedByDescendingDate();
		}
		
		return manifestationService.read();
	}
	
	@GET
	@Path("manifestations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse readById(@PathParam("id") UUID id) {
		Manifestation manifestation = manifestationService.read(id);
		
		return generateManifestationObjectResponse(manifestation);
	}

	@POST
	@Path("manifestations/advance-search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manifestation> advanceSearch(ManifestationsSearchParamethers searchParamethers) {
		super.validateRequest(searchParamethers);
		
		return searchService.search(searchParamethers);
	}
	
	@POST
	@Path("manifestations/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse create(CreateManifestationRequest request) {
		super.validateRequest(request);
		
		Manifestation manifestation = mapper.Map(new Manifestation(), request);
		
		Manifestation createdManifestation = manifestationService.create(manifestation);
		
		return generateManifestationObjectResponse(createdManifestation);
	}

	@PUT
	@Path("manifestations/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse update(UpdateManifestationRequest request) {
		super.validateRequest(request);

		Manifestation manifestation = manifestationService.read(request.getId());
		if(manifestation == null) {
			return null;
		}
		
		Manifestation manifestationForUpdate = mapper.Map(manifestation, request);

		Manifestation updatedManifestation = manifestationService.update(manifestationForUpdate);
		
		return generateManifestationObjectResponse(updatedManifestation);
	}
	
	@DELETE
	@Path("manifestations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return manifestationService.delete(id);
	}
	
	private WholeManifestationObjectResponse generateManifestationObjectResponse(Manifestation manifestation)
	{
		return mapper.Map(new WholeManifestationObjectResponse(), manifestation);
	}
}
