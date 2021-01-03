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
import core.repository.IRepository;
import core.requests.buyerTypes.CreateBuyerTypeRequest;
import core.requests.buyerTypes.UpdateBuyerTypeRequest;
import core.service.ICrudService;
import core.servlets.IMapper;
import repository.DbContext;
import repository.Repository;
import services.CrudService;
import servlets.utils.mapper.ObjectMapper;

@Path("buyer-type")
public class BuyerTypesServlet {
	@Context
	ServletContext servletContext;
	
	private ICrudService<BuyerType> buyerTypeService;
	private IMapper mapper;

	public BuyerTypesServlet()
	{
		mapper = new ObjectMapper();
	}
	
	@PostConstruct
	public void init()
	{
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);
		buyerTypeService = new CrudService<BuyerType>(buyerTypeRepository);
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
		return buyerTypeService.read(id);
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType create(CreateBuyerTypeRequest request)
	{
		BuyerType buyerType = mapper.Map(new BuyerType(), request);
		
		return buyerTypeService.create(buyerType);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType update(UpdateBuyerTypeRequest request)
	{
		BuyerType buyerType = buyerTypeService.read(request.getId());
		if(buyerType == null) {
			return null;
		}
		BuyerType buyerTypeForUpdate = mapper.Map(buyerType, request);
		
		return buyerTypeService.update(buyerTypeForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return buyerTypeService.delete(id);
	}
}
