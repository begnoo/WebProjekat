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

import core.domain.models.Ticket;
import core.repository.IRepository;
import core.requests.tickets.CreateTicketRequest;
import core.requests.tickets.UpdateTicketRequest;
import core.responses.tickets.WholeTicketObjectResponse;
import core.servlets.IMapper;
import repository.DbContext;
import repository.TicketRepository;
import servlets.utils.mapper.ObjectMapper;

@Path("tickets")
public class TicketServlet {

	private IRepository<Ticket> ticketRepository;
	private IMapper mapper;

	@Context
	ServletContext servletContext;

	public TicketServlet() {
		mapper = new ObjectMapper();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		ticketRepository = new TicketRepository(context);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Ticket> readAll() {
		return ticketRepository.read();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse readById(@PathParam("id") UUID id) {
		Ticket ticket = ticketRepository.read(id);
		
		return generateTicketObjectResponse(ticket);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse create(CreateTicketRequest request) {
		Ticket ticket = mapper.Map(new Ticket(), request);

		Ticket createdTicket = ticketRepository.create(ticket);
	
		return generateTicketObjectResponse(createdTicket);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeTicketObjectResponse update(UpdateTicketRequest request) {
		Ticket ticketForUpdate = mapper.Map(ticketRepository.read(request.getId()), request);

		Ticket updatedTicket = ticketRepository.update(ticketForUpdate);

		return generateTicketObjectResponse(updatedTicket);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return ticketRepository.delete(id);
	}

	private WholeTicketObjectResponse generateTicketObjectResponse(Ticket ticket)
	{
		return mapper.Map(new WholeTicketObjectResponse(), ticket);
	}
	
}

