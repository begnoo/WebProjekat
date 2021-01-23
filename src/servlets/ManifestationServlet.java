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
import core.domain.dto.Page;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.requests.manifestations.CreateManifestationRequest;
import core.requests.manifestations.UpdateManifestationRequest;
import core.responses.manifestations.WholeManifestationObjectResponse;
import core.service.IAdvanceSearchService;
import core.service.IManifestationService;
import core.service.IPaginationService;
import repository.DbContext;
import repository.ManifestationRepository;
import services.ManifestationSearchService;
import services.PaginationService;

@Path("/")
public class ManifestationServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private IManifestationService manifestationService;
	private IAdvanceSearchService<Manifestation, ManifestationsSearchParamethers> searchService;
	private IPaginationService<Manifestation> paginationService;

	public ManifestationServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		manifestationService = (IManifestationService) serviceFactory.getService(IManifestationService.class, context);

		paginationService = new PaginationService<Manifestation>();
				
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		searchService = new ManifestationSearchService(manifestationRepository);
	}

	@GET
	@Path("manifestations/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manifestation> readAll(@QueryParam("order-by-date") boolean orderByDate, @QueryParam("number") int number, @QueryParam("size") int size) {
		List<Manifestation> manifestations = null;
		if(orderByDate) {
			manifestations = manifestationService.readOrderedByDescendingDate();
		} else {
			manifestations = manifestationService.read();
		}
		
		return paginationService.readPage(manifestations, new Page(number, size));
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
	public List<Manifestation> advanceSearch(ManifestationsSearchParamethers searchParamethers, @QueryParam("number") int number, @QueryParam("size") int size) {
		super.validateRequest(searchParamethers);
		
		List<Manifestation> manifestations = searchService.search(searchParamethers);
		
		return paginationService.readPage(manifestations, new Page(number, size));
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
