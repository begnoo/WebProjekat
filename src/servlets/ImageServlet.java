package servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.models.Manifestation;
import core.repository.IRepository;
import core.requests.images.Base64ImageForManifestation;
import core.responses.manifestations.WholeManifestationObjectResponse;
import core.service.IImageService;
import repository.DbContext;
import repository.ManifestationRepository;
import services.ImageService;

@Path("images")
public class ImageServlet extends AbstractServletBase {
	
	@Context
	ServletContext servletContext;

	private IImageService imageService;
	
	public ImageServlet()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		imageService = new ImageService(manifestationRepository);
	}
	
	@GET
	@Path("/{file-name}")
	@Produces("image/jpeg")
	public BufferedInputStream createImageForManifestation(@PathParam("file-name") String fileName) {
		String filePath = "images/" + fileName;
		try {
			return new BufferedInputStream(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			return null;
		}

	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse updateImageForManifestation(Base64ImageForManifestation request) {
		Manifestation manifestationWithImage = imageService.updateImageForManifestation(request);
		
		return generateManifestationObjectResponse(manifestationWithImage);
	}
		
	private WholeManifestationObjectResponse generateManifestationObjectResponse(Manifestation manifestation)
	{
		return mapper.Map(new WholeManifestationObjectResponse(), manifestation);
	}
}
