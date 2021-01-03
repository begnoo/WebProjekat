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
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.requests.tickets.UpdateTicketRequest;
import core.responses.tickets.WholeTicketObjectResponse;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import core.servlets.IMapper;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.TicketRepository;
import repository.UserRepository;
import services.TicketOrderService;
import services.TicketService;
import servlets.utils.mapper.ObjectMapper;

@Path("tickets")
public class TicketServlet {
	@Context
	ServletContext servletContext;

	private ITicketService ticketService;
	private ITicketOrderService ticketOrderService;
	private IMapper mapper;

	public TicketServlet() {
		mapper = new ObjectMapper();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Ticket> ticketRepository = new TicketRepository(context);
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		ticketService = new TicketService(ticketRepository, userRepository, manifestationRepository);
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
		List<Ticket> createdTickets = ticketOrderService.createTicketsFromOrder(ticketOrder);
		
		List<WholeTicketObjectResponse> response = createdTickets.stream()
				.map(ticket -> generateTicketObjectResponse(ticket))
				.collect(Collectors.toList());
						
		return response;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse update(UpdateTicketRequest request) {
		Ticket ticketForUpdate = mapper.Map(ticketService.read(request.getId()), request);

		Ticket updatedTicket = ticketService.update(ticketForUpdate);

		return generateTicketObjectResponse(updatedTicket);
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

