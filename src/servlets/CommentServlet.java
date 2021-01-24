package servlets;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
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
import servlets.utils.filters.UserSpecificEntity;

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
	
	@GET
	@Path("manifestations/{manifestationId}/comments")
	@Authorize(roles = "Administrator,Seller")
	@UserSpecificEntity(what = "Manifestation", belongsTo = "Seller")
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
	
	@GET
	@Path("manifestations/{manifestationId}/comments/approved")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WholeCommentObjectResponse> readApprovedCommentsByManifestationId(@PathParam("manifestationId") UUID manifestationId, @QueryParam("number") int number, @QueryParam("size") int size)
	{
		List<Comment> commentsForManifestation = commentService.readByManifestationIdAndStatus(manifestationId, CommentStatus.Approved);
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

	@PUT
	@Path("comments/")
	@Authorize(roles = "Buyer")
	@UserSpecificEntity(what = "Comment", belongsTo = "Buyer")
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
		
	private WholeCommentObjectResponse generateCommentObjectResponse(Comment comment)
	{
		return mapper.Map(new WholeCommentObjectResponse(), comment);
	}

}