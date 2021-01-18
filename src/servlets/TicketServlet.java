package servlets;

import java.util.HashMap;
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

import core.domain.dto.TicketOrder;
import core.domain.dto.TicketsSearchParamethers;
import core.domain.enums.TicketType;
import core.domain.models.BuyerType;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.responses.tickets.WholeTicketObjectResponse;
import core.service.IAdvanceSearchService;
import core.service.IBuyerTypeService;
import core.service.IManifestationService;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import core.service.IUserService;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import repository.TicketRepository;
import repository.UserRepository;
import services.BuyerTypeService;
import services.ManifestationService;
import services.TicketOrderService;
import services.TicketService;
import services.TicketsSearchService;
import services.UserService;

@Path("/")
public class TicketServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private ITicketService ticketService;
	private ITicketOrderService ticketOrderService;
	private IAdvanceSearchService<Ticket, TicketsSearchParamethers> searchService;

	public TicketServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Ticket> ticketRepository = new TicketRepository(context);
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		
		IBuyerTypeService buyerTypeService = new BuyerTypeService(buyerTypeRepository);
		IUserService userService = new UserService(userRepository, buyerTypeService);
		IManifestationService manifestationService = new ManifestationService(manifestationRepository, locationRepository);
		ticketService = new TicketService(ticketRepository, userService, manifestationService);
		ticketOrderService = new TicketOrderService(ticketService, userRepository, manifestationRepository, buyerTypeRepository);
		searchService = new TicketsSearchService(ticketRepository);
	}

	@GET
	@Path("tickets/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> readAll() {
		return ticketService.read();
	}

	@GET
	@Path("tickets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse readById(@PathParam("id") UUID id) {
		Ticket ticket = ticketService.read(id);
		
		return generateTicketObjectResponse(ticket);
	}

	@GET
	@Path("manifestations/{manifestationId}/tickets")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> readByManifestationId(@PathParam("manifestationId") UUID manifestationId) {
		List<Ticket> ticketsForManifestation = ticketService.readByManifestationId(manifestationId);
		
		List<WholeTicketObjectResponse> wholeTicketObjectsForManifestation = ticketsForManifestation.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsForManifestation;
	}
	
	@GET
	@Path("users/sellers/{sellerId}/manifestations/tickets/reserved")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> readReservedTicketsOfSellersManifestations(@PathParam("sellerId") UUID sellerId) {
		List<Ticket> ticketsForSellersManifestation = ticketService.readReservedTicketsOfSellersManifestations(sellerId);
		
		List<WholeTicketObjectResponse> wholeTicketObjectsForSellersManifestation = ticketsForSellersManifestation.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsForSellersManifestation;
	}
	
	@GET
	@Path("users/buyers/{buyerId}/tickets")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> readByBuyerId(@PathParam("buyerId") UUID buyerId, @QueryParam("only-reserved") boolean onlyReserved) {
		List<Ticket> ticketsOfBuyer;
		if(onlyReserved) {
			ticketsOfBuyer = ticketService.readReservedTicketsOfBuyer(buyerId);
		} else {
			ticketsOfBuyer = ticketService.readByBuyerId(buyerId);
		}
		
		List<WholeTicketObjectResponse> wholeTicketObjectsOfBuyers = ticketsOfBuyer.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsOfBuyers;
	}
	
	@GET
	@Path("tickets/buyer-type/{buyerTypeId}/prices/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<TicketType, Integer> getTicketPricesForBuyerType(@PathParam("buyerTypeId") UUID buyerTypeId, @PathParam("price") int regularPrice){
		return ticketOrderService.getTicketPrices(regularPrice, buyerTypeId);
	}
	
	@POST
	@Path("tickets/advance-search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> advanceSearch(TicketsSearchParamethers searchParamethers) {
		super.validateRequest(searchParamethers);
		
		return searchService.search(searchParamethers);
	}
	
	@POST
	@Path("tickets/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> create(TicketOrder ticketOrder) {
		super.validateRequest(ticketOrder);
		
		List<Ticket> createdTickets = ticketOrderService.createTicketsFromOrder(ticketOrder);
		
		List<WholeTicketObjectResponse> response = createdTickets.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
						
		return response;
	}

	@PUT
	@Path("tickets/{id}/cancel")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse cancelTicket(@PathParam("id") UUID id) {
		Ticket canceledTicket = ticketService.cancelTicket(id);
		if(canceledTicket == null) {
			return null;
		}
		return generateTicketObjectResponse(canceledTicket);
	}
	
	@DELETE
	@Path("tickets/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return ticketService.delete(id);
	}

	private WholeTicketObjectResponse generateTicketObjectResponse(Ticket ticket)
	{
		return mapper.Map(new WholeTicketObjectResponse(), ticket);
	}
		
}