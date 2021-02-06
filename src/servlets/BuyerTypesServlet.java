package servlets;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.models.BuyerType;
import core.service.IBuyerTypeService;
import core.servlets.exceptions.NotFoundException;
import repository.DbContext;
import servlets.utils.filters.Authorize;

//TODO: OBRISI
@Path("buyer-type")
public class BuyerTypesServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;
	
	private IBuyerTypeService buyerTypeService;

	public BuyerTypesServlet()
	{
		super();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		buyerTypeService = (IBuyerTypeService) serviceFactory.getService(IBuyerTypeService.class, context);
	}
	
	
	@GET
	@Authorize(roles = "Buyer")
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType readById(@PathParam("id") UUID id)
	{
		BuyerType buyerType = buyerTypeService.read(id);
		if(buyerType == null) {
			throw new NotFoundException("BuyerType does not exists.");
		}
		
		return buyerType;
	}
	
}