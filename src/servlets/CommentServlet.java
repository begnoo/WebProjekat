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

import core.domain.models.Comment;
import core.domain.models.Manifestation;
import core.domain.models.User;
import core.repository.IRepository;
import core.requests.comments.CreateCommentRequest;
import core.requests.comments.UpdateCommentRequest;
import core.servlets.IMapper;
import repository.DbContext;
import repository.Repository;
import services.CommentService;
import servlets.utils.mapper.ObjectMapper;

@Path("comments")
public class CommentServlet {

	private CommentService commentService;
	private IMapper mapper;

	@Context
	ServletContext servletContext;

	public CommentServlet() {
		mapper = new ObjectMapper();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Comment> commentRepository = new Repository<Comment>(context, Comment.class);
		commentService = new CommentService(commentRepository);
		
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> readAll() {
		return commentService.read();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment readById(@PathParam("id") UUID id) {
		return commentService.read(id);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment create(CreateCommentRequest request) {
		Comment comment = mapper.Map(new Comment(), request);

		return commentService.create(comment);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comment update(UpdateCommentRequest request) {
		Comment commentForUpdate = mapper.Map(commentService.read(request.getId()), request);

		return commentService.update(commentForUpdate);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return commentService.delete(id);
	}

}

