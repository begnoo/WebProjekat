package servlets;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
import core.responses.manifestations.ManifestationRatingResponse;
import core.responses.manifestations.WholeManifestationObjectResponse;
import core.service.IAdvanceSearchService;
import core.service.IManifestationService;
import core.service.IPaginationService;
import core.servlets.exceptions.NotFoundException;
import repository.DbContext;
import repository.ManifestationRepository;
import services.ManifestationSearchService;
import services.PaginationService;
import servlets.utils.filters.Authorize;
import servlets.utils.filters.UserSpecificEntity;

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
	@Path("manifestations/suggestions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeManifestationObjectResponse> readSuggestions(@QueryParam("number") int number, @QueryParam("size") int size) {
		List<Manifestation> manifestations = manifestationService.readSuggestions();
		List<Manifestation> paginatedManifestation = paginationService.readPage(manifestations, new Page(number, size));
		
		List<WholeManifestationObjectResponse> WholeManifestatiosnObjectResponse = paginatedManifestation.stream()
				.map(manifestation -> generateManifestationObjectResponse(manifestation))
				.collect(Collectors.toList());

		
		return WholeManifestatiosnObjectResponse;
	}
	
	@GET
	@Path("manifestations/location/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeManifestationObjectResponse> readManifestationsByLocationId(@PathParam("id") UUID locationId, @QueryParam("number") int number, @QueryParam("size") int size) {
		List<Manifestation> manifestations = manifestationService.readByLocationIdInFollowingWeek(locationId);
		List<Manifestation> paginatedManifestation = paginationService.readPage(manifestations, new Page(number, size));
		
		List<WholeManifestationObjectResponse> WholeManifestatiosnObjectResponse = paginatedManifestation.stream()
				.map(manifestation -> generateManifestationObjectResponse(manifestation))
				.collect(Collectors.toList());

		
		return WholeManifestatiosnObjectResponse;
	}
	
	@GET
	@Path("manifestations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse readById(@PathParam("id") UUID id) {
		Manifestation manifestation = manifestationService.read(id);
		if(manifestation == null) {
			throw new NotFoundException("Manifestation does not exists.");
		}
		
		return generateManifestationObjectResponse(manifestation);
	}
	
	@GET
	@Path("manifestations/{id}/rating")
	@Produces(MediaType.APPLICATION_JSON)
	public ManifestationRatingResponse readRating(@PathParam("id") UUID id) {
		int rating = manifestationService.getRating(id);
		
		return new ManifestationRatingResponse(id, rating);
	}

	@POST
	@Path("manifestations/advance-search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeManifestationObjectResponse> advanceSearch(ManifestationsSearchParamethers searchParamethers, @QueryParam("number") int number, @QueryParam("size") int size) {
		super.validateRequest(searchParamethers);
		
		List<Manifestation> manifestations = searchService.search(searchParamethers);
		List<Manifestation> paginatedManifestation = paginationService.readPage(manifestations, new Page(number, size));
		
		List<WholeManifestationObjectResponse> wholeTicketObjectsForManifestation = paginatedManifestation.stream()
				.map(manifestation -> generateManifestationObjectResponse(manifestation))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsForManifestation;
	}
	
	@POST
	@Path("manifestations/")
	@Authorize(roles = "Seller")
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
	@Authorize(roles = "Seller")
	@UserSpecificEntity(what = "Manifestation", belongsTo = "Seller")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse update(UpdateManifestationRequest request) {
		super.validateRequest(request);

		Manifestation manifestation = manifestationService.read(request.getId());
		if(manifestation == null) {
			throw new NotFoundException("Manifestation does not exists.");
		}
		
		Manifestation manifestationForUpdate = mapper.Map(manifestation, request);

		Manifestation updatedManifestation = manifestationService.update(manifestationForUpdate);
		
		return generateManifestationObjectResponse(updatedManifestation);
	}
	
	@PUT
	@Path("manifestations/{id}/approve")
	@Authorize(roles = "Administrator")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse approve(@PathParam("id") UUID id) {

		Manifestation approvedManifestation = manifestationService.approve(id);
		
		return generateManifestationObjectResponse(approvedManifestation);
	}
	
	@DELETE
	@Path("manifestations/{manifestationId}")
	@Authorize(roles = "Administrator,Seller")
	@UserSpecificEntity(what = "Manifestation", belongsTo = "Seller")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation delete(@PathParam("manifestationId") UUID id)
	{
		return manifestationService.delete(id);
	}
	
	private WholeManifestationObjectResponse generateManifestationObjectResponse(Manifestation manifestation)
	{
		return mapper.Map(new WholeManifestationObjectResponse(), manifestation);
	}
}
