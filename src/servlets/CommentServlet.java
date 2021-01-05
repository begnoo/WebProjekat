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
import core.responses.comments.WholeCommentObjectResponse;
import core.service.ICommentService;
import repository.CommentRepository;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.UserRepository;
import services.CommentService;

@Path("comments")
public class CommentServlet extends AbstractServletBase {

	@Context
	ServletContext servletContext;

	private ICommentService commentService;
	
	public CommentServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		IRepository<Comment> commentRepository = new CommentRepository(context);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<User> userRepository = new UserRepository(context);
		commentService = new CommentService(commentRepository, manifestationRepository, userRepository);
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
	public WholeCommentObjectResponse readById(@PathParam("id") UUID id) {
		Comment comment = commentService.read(id);
		
		return generateCommentObjectResponse(comment);
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeCommentObjectResponse create(CreateCommentRequest request) {
		super.validateRequest(request);
		
		Comment comment = mapper.Map(new Comment(), request);

		Comment createdComment = commentService.create(comment);
		
		return generateCommentObjectResponse(createdComment);
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeCommentObjectResponse update(UpdateCommentRequest request) {
		super.validateRequest(request);
		
		Comment comment = commentService.read(request.getId());
		if(comment == null) {
			return null;
		}
		Comment commentForUpdate = mapper.Map(comment, request);

		Comment updatedComment = commentService.update(commentForUpdate);
		
		return generateCommentObjectResponse(updatedComment);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean delete(@PathParam("id") UUID id)
	{
		return commentService.delete(id);
	}
	
	private WholeCommentObjectResponse generateCommentObjectResponse(Comment comment)
	{
		return mapper.Map(new WholeCommentObjectResponse(), comment);
	}

}