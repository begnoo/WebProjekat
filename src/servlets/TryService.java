package servlets;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.TestObject;
import core.domain.models.Manifestation;
import core.repository.IRepository;
import repository.DbContext;
import repository.Repository;
import services.ManifestationService;

@Path("/try")
public class TryService {

	@Context
	ServletContext servletContext;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TestObject> getTestObjects() {
		Collection<TestObject> testObjects = new ArrayList<>();

		testObjects.add(new TestObject(22));
		testObjects.add(new TestObject(11));
		testObjects.add(new TestObject(33));

		return testObjects;
	}

	@GET
	@Path("/manifestations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestaion() {
		DbContext dbContext = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Manifestation> manifestationRepository = new Repository<>(dbContext, Manifestation.class);
		ManifestationService manifestationService = new ManifestationService(manifestationRepository);

		return manifestationService.read();
	}
}
