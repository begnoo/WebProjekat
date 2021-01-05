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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.dto.TicketOrder;
import core.domain.models.BuyerType;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.responses.tickets.WholeTicketObjectResponse;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import repository.TicketRepository;
import repository.UserRepository;
import services.TicketOrderService;
import services.TicketService;

@Path("tickets")
public class TicketServlet extends AbstractServletBase {
	@Context
	ServletContext servletContext;

	private ITicketService ticketService;
	private ITicketOrderService ticketOrderService;

	public TicketServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Ticket> ticketRepository = new TicketRepository(context);
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<BuyerType> buyerTypeRepository = new Repository<>(context, BuyerType.class);
		ticketService = new TicketService(ticketRepository, userRepository, manifestationRepository, buyerTypeRepository);
		ticketOrderService = new TicketOrderService(ticketService, userRepository, manifestationRepository);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> readAll() {
		return ticketService.read();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse readById(@PathParam("id") UUID id) {
		Ticket ticket = ticketService.read(id);
		
		return generateTicketObjectResponse(ticket);
	}

	@POST
	@Path("/")
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
	@Path("/{id}/cancel")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse cancelTicket(@PathParam("id") UUID id) {
		Ticket canceledTicket = ticketService.cancelTicket(id);
		if(canceledTicket == null) {
			return null;
		}
		return generateTicketObjectResponse(canceledTicket);
	}
	
	@DELETE
	@Path("/{id}")
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