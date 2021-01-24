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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.dto.Page;
import core.domain.dto.TicketOrder;
import core.domain.dto.TicketsSearchParamethers;
import core.domain.enums.TicketType;
import core.domain.models.Ticket;
import core.repository.IRepository;
import core.responses.tickets.WholeTicketObjectResponse;
import core.service.IAdvanceSearchService;
import core.service.IPaginationService;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import repository.DbContext;
import repository.TicketRepository;
import services.PaginationService;
import services.TicketsSearchService;
import servlets.utils.filters.Authorize;
import servlets.utils.filters.UserSpecificEntity;

@Path("/")
public class TicketServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private ITicketService ticketService;
	private ITicketOrderService ticketOrderService;
	private IAdvanceSearchService<Ticket, TicketsSearchParamethers> searchService;
	private IPaginationService<Ticket> paginationService;

	public TicketServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		ticketService = (ITicketService) serviceFactory.getService(ITicketService.class, context);
		ticketOrderService = (ITicketOrderService) serviceFactory.getService(ITicketOrderService.class, context);
		
		paginationService = new PaginationService<Ticket>();

		IRepository<Ticket> ticketRepository = new TicketRepository(context);
		searchService = new TicketsSearchService(ticketRepository);
	}

	@GET
	@Path("tickets/")
	@Authorize(roles = "Administrator")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> readAll(@QueryParam("number") int number, @QueryParam("size") int size) {
		List<Ticket> tickets = ticketService.read();
		
		return paginationService.readPage(tickets, new Page(number, size)); 
	}

	@GET
	@Path("manifestations/{manifestationId}/tickets")
	@Authorize(roles = "Administrator,Seller")
	@UserSpecificEntity(what = "Manifestation", belongsTo = "Seller")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> readByManifestationId(@PathParam("manifestationId") UUID manifestationId, @QueryParam("number") int number, @QueryParam("size") int size) {
		List<Ticket> ticketsForManifestation = ticketService.readByManifestationId(manifestationId);
		List<Ticket> paginatedTickets = paginationService.readPage(ticketsForManifestation, new Page(number, size));
		
		List<WholeTicketObjectResponse> wholeTicketObjectsForManifestation = paginatedTickets.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsForManifestation;
	}
	
	// TODO: SELLER SAMO SVOJE
	@GET
	@Path("users/sellers/{sellerId}/manifestations/tickets/reserved")
	@Authorize(roles = "Administrator,Seller")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> readReservedTicketsOfSellersManifestations(@PathParam("sellerId") UUID sellerId, @QueryParam("number") int number, @QueryParam("size") int size) {
		List<Ticket> ticketsForSellersManifestation = ticketService.readReservedTicketsOfSellersManifestations(sellerId);
		List<Ticket> paginatedTickets = paginationService.readPage(ticketsForSellersManifestation, new Page(number, size));
		
		List<WholeTicketObjectResponse> wholeTicketObjectsForSellersManifestation = paginatedTickets.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsForSellersManifestation;
	}
	
	// TODO: BUYER SAMO SVOJE
	@GET
	@Path("users/buyers/{buyerId}/tickets")
	@Authorize(roles = "Administrator,Buyer")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeTicketObjectResponse> readByBuyerId(@PathParam("buyerId") UUID buyerId, @QueryParam("only-reserved") boolean onlyReserved, @QueryParam("number") int number, @QueryParam("size") int size) {
		List<Ticket> ticketsOfBuyer;
		if(onlyReserved) {
			ticketsOfBuyer = ticketService.readReservedTicketsOfBuyer(buyerId);
		} else {
			ticketsOfBuyer = ticketService.readByBuyerId(buyerId);
		}
		List<Ticket> paginatedTickets = paginationService.readPage(ticketsOfBuyer, new Page(number, size));

		List<WholeTicketObjectResponse> wholeTicketObjectsOfBuyers = paginatedTickets.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
		
		return wholeTicketObjectsOfBuyers;
	}
	
	@GET
	@Path("tickets/prices")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<TicketType, Integer> getTicketPrices(@QueryParam("buyerTypeId") UUID buyerTypeId, @QueryParam("price") int regularPrice){
		if(buyerTypeId == null) {
			return ticketOrderService.getTicketPricesWithoutDiscount(regularPrice);
		}
		
		return ticketOrderService.getTicketPricesWithBuyerDiscount(regularPrice, buyerTypeId);
	}
	
	@POST
	@Path("tickets/advance-search")
	@Authorize()
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> advanceSearch(TicketsSearchParamethers searchParamethers, @QueryParam("number") int number, @QueryParam("size") int size) {
		super.validateRequest(searchParamethers);
		
		List<Ticket> tickets = searchService.search(searchParamethers);
		
		return paginationService.readPage(tickets, new Page(number, size));
	}
	
	@POST
	@Path("tickets/")
	@Authorize(roles = "Buyer")
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

	@DELETE
	@Path("tickets/{ticketId}")
	@Authorize(roles = "Buyer")
	@UserSpecificEntity(what = "Ticket", belongsTo = "Buyer")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse cancelTicket(@PathParam("ticketId") UUID id) {
		Ticket canceledTicket = ticketService.cancelTicket(id);
		if(canceledTicket == null) {
			return null;
		}
		return generateTicketObjectResponse(canceledTicket);
	}
	
	private WholeTicketObjectResponse generateTicketObjectResponse(Ticket ticket)
	{
		return mapper.Map(new WholeTicketObjectResponse(), ticket);
	}
		
}