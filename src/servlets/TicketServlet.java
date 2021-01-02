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
import core.servlets.IMapper;
import repository.DbContext;
import repository.Repository;
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
		ticketRepository = new Repository<Ticket>(context, Ticket.class);
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
	public Ticket readById(@PathParam("id") UUID id) {
		return ticketRepository.read(id);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket create(CreateTicketRequest request) {
		Ticket manifestation = mapper.Map(new Ticket(), request);

		return ticketRepository.create(manifestation);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket update(UpdateTicketRequest request) {
		Ticket ticketForUpdate = mapper.Map(ticketRepository.read(request.getId()), request);

		return ticketRepository.update(ticketForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return ticketRepository.delete(id);
	}

}

