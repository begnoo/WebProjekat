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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import core.domain.dto.Page;
import core.domain.enums.CommentStatus;
import core.domain.models.Comment;
import core.requests.comments.CreateCommentRequest;
import core.requests.comments.UpdateCommentRequest;
import core.responses.comments.WholeCommentObjectResponse;
import core.service.ICommentService;
import core.service.IPaginationService;
import core.servlets.exceptions.NotFoundException;
import repository.DbContext;
import services.PaginationService;
import servlets.utils.filters.Authorize;

@Path("/")
public class CommentServlet extends AbstractServletBase {

	@Context
	ServletContext servletContext;

	private ICommentService commentService;
	private IPaginationService<Comment> paginationService;
	
	public CommentServlet()
	{
		super();
	}

	@PostConstruct
	public void init() {
		DbContext context = (DbContext) servletContext.getAttribute("DbContext");
		
		commentService = (ICommentService) serviceFactory.getService(ICommentService.class, context);
		
		paginationService = new PaginationService<Comment>();
	}
	
	// TODO: SELLER SAMO OD SVOJIH MANIFESTACIJA, ADMIN SVE
	@GET
	@Path("manifestations/{manifestationId}/comments")
	@Authorize(roles = "Administrator,Seller")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeCommentObjectResponse> readByManifestationId(@PathParam("manifestationId") UUID manifestationId, @QueryParam("status") CommentStatus commentStatus, @QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<Comment> commentsForManifestation;
		if(commentStatus == null) {
			commentsForManifestation = commentService.readByManifestationId(manifestationId);
		} else {
			commentsForManifestation = commentService.readByManifestationIdAndStatus(manifestationId, commentStatus);
		}
		List<Comment> paginatedComments = paginationService.readPage(commentsForManifestation, new Page(number, size));
		
		List<WholeCommentObjectResponse> wholeCommentObjectsForManifestation = paginatedComments.stream()
				.map(comment -> generateCommentObjectResponse(comment))
				.collect(Collectors.toList());
		
		return wholeCommentObjectsForManifestation;
	}
	
	// TODO: CHANGE TO approved i ne mora biti autentifikovan
	@GET
	@Path("manifestations/{manifestationId}/comments/non-pending")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeCommentObjectResponse> readNonPendingCommentsByManifestationId(@PathParam("manifestationId") UUID manifestationId, @QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<Comment> commentsForManifestation = commentService.readNonPendingCommentsByManifestationId(manifestationId);
		List<Comment> paginatedComments = paginationService.readPage(commentsForManifestation, new Page(number, size));

		List<WholeCommentObjectResponse> wholeCommentObjectsForManifestation = paginatedComments.stream()
				.map(comment -> generateCommentObjectResponse(comment))
				.collect(Collectors.toList());
		
		return wholeCommentObjectsForManifestation;
	}

	@POST
	@Path("comments/")
	@Authorize(roles = "Buyer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeCommentObjectResponse create(CreateCommentRequest request) {
		super.validateRequest(request);
		
		Comment comment = mapper.Map(new Comment(), request);

		Comment createdComment = commentService.create(comment);
		
		return generateCommentObjectResponse(createdComment);
	}

	// TODO: BUYER I AKO SE PROMENO STATUS PONOVO IDE NA PENDING
	// TODO: BUYER MOZE MENJATI SAMO SVOJE KOMENTARE
	@PUT
	@Path("comments/")
	@Authorize(roles = "Buyer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public WholeCommentObjectResponse update(UpdateCommentRequest request) {
		super.validateRequest(request);
		
		Comment comment = commentService.read(request.getId());
		if(comment == null) {
			throw new NotFoundException("Comment does not exists.");
		}
		Comment commentForUpdate = mapper.Map(comment, request);

		Comment updatedComment = commentService.update(commentForUpdate);
		
		return generateCommentObjectResponse(updatedComment);
	}
	
	// TODO: SELER MOZE MENJATI SAMO KOMENTAR KOJI PRIPADA NJEGOVOJ MANIFESTACIJI
	@PUT
	@Path("comments/{id}/status")
	@Authorize(roles = "Seller")
	@Produces(MediaType.APPLICATION_JSON)
	public WholeCommentObjectResponse update(@PathParam("id") UUID id, @QueryParam("status") CommentStatus status) {
		Comment updatedComment = commentService.changeStatus(id, status);
		
		return generateCommentObjectResponse(updatedComment);
	}
	
	// TODO: KOMENTAR MORA PRIPADATI NJEGOJ MANIFESTACIJI
	@DELETE
	@Path("comments/{id}")
	@Authorize(roles = "Seller")
	@Produces(MediaType.APPLICATION_JSON)
	public Comment delete(@PathParam("id") UUID id)
	{
		return commentService.delete(id);
	}
	
	private WholeCommentObjectResponse generateCommentObjectResponse(Comment comment)
	{
		return mapper.Map(new WholeCommentObjectResponse(), comment);
	}

}