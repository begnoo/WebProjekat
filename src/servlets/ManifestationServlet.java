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

import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.requests.manifestations.CreateManifestationRequest;
import core.requests.manifestations.UpdateManifestationRequest;
import core.servlets.IMapper;
import repository.DbContext;
import repository.Repository;
import servlets.utils.mapper.ObjectMapper;

@Path("manifestations")
public class ManifestationServlet {

	private IRepository<Manifestation> manifestationRepository;
	private IMapper mapper;

	@Context
	ServletContext servletContext;

	public ManifestationServlet() {
		mapper = new ObjectMapper();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		manifestationRepository = new Repository<Manifestation>(context, Manifestation.class);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Manifestation> readAll() {
		return manifestationRepository.read();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation readById(@PathParam("id") UUID id) {
		return manifestationRepository.read(id);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation create(CreateManifestationRequest request) {
		Manifestation manifestation = mapper.Map(new Manifestation(), request);

		return manifestationRepository.create(manifestation);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation update(UpdateManifestationRequest request) {
		Manifestation manifestationForUpdate = mapper.Map(manifestationRepository.read(request.getId()), request);

		return manifestationRepository.update(manifestationForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return manifestationRepository.delete(id);
	}

}
