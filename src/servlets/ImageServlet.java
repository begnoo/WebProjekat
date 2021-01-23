package servlets;

import java.io.BufferedInputStream;

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
import core.requests.images.Base64ImageForManifestation;
import core.responses.manifestations.WholeManifestationObjectResponse;
import core.service.IImageService;
import repository.DbContext;

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
		
		imageService = (IImageService) serviceFactory.getService(IImageService.class, context);
	}
	
	@GET
	@Path("/{file-name}")
	@Produces("image/jpeg")
	public BufferedInputStream getImage(@PathParam("file-name") String fileName) {
		return imageService.getImage(fileName);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeManifestationObjectResponse updateImage(Base64ImageForManifestation request) {
		super.validateRequest(request);
		
		Manifestation manifestationWithImage = imageService.updateImageForManifestation(request);
		
		return generateManifestationObjectResponse(manifestationWithImage);
	}
		
	private WholeManifestationObjectResponse generateManifestationObjectResponse(Manifestation manifestation)
	{
		return mapper.Map(new WholeManifestationObjectResponse(), manifestation);
	}
}
