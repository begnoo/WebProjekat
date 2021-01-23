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

import core.domain.models.BuyerType;
import core.requests.buyerTypes.CreateBuyerTypeRequest;
import core.requests.buyerTypes.UpdateBuyerTypeRequest;
import core.service.IBuyerTypeService;
import core.servlets.exceptions.NotFoundException;
import repository.DbContext;

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
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BuyerType> readAll()
	{
		return buyerTypeService.read();
	}
	
	@GET
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
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType create(CreateBuyerTypeRequest request)
	{
		super.validateRequest(request);
		
		BuyerType buyerType = mapper.Map(new BuyerType(), request);
		
		return buyerTypeService.create(buyerType);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType update(UpdateBuyerTypeRequest request)
	{
		super.validateRequest(request);

		BuyerType buyerType = buyerTypeService.read(request.getId());
		if(buyerType == null) {
			throw new NotFoundException("BuyerType does not exists.");
		}
		BuyerType buyerTypeForUpdate = mapper.Map(buyerType, request);
		
		return buyerTypeService.update(buyerTypeForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType delete(@PathParam("id") UUID id)
	{
		return buyerTypeService.delete(id);
	}
}
