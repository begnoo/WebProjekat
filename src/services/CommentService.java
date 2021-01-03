package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.CommentStatus;
import core.domain.enums.TicketStatus;
import core.domain.models.Buyer;
import core.domain.models.Comment;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ICommentService;

public class CommentService extends CrudService<Comment> implements ICommentService{
	
	
	public CommentService(IRepository<Comment> repository) {
		super(repository);
	}
	
	@Override
	public Comment create(Comment comment) {
		Manifestation manifestation = comment.getManifestation();
		Buyer buyer = (Buyer) comment.getBuyer();
		
		List<Ticket> reservedTickets = buyer.getTickets()
			.stream()
			.filter(ticket -> ticket.getManifestationId().equals(comment.getManifestationId()))
			.filter(ticket -> ticket.getStatus() == TicketStatus.Reserved)
			.collect(Collectors.toList());

		if(manifestation.getEventDate().isAfter(LocalDateTime.now()) || reservedTickets.isEmpty()) {
			return null;
		}
		
		comment.setStatus(CommentStatus.Pending);
		
		return repository.create(comment);
	}

	@Override
	public List<Comment> readCommentsByManifestationId(UUID manifestationId) {
		return repository.read()
				.stream()
				.filter(comment -> comment.getManifestationId().equals(manifestationId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Comment> readNonPendingCommentsByManifestationId(UUID manifestationId) {
		return readCommentsByManifestationId(manifestationId)
				.stream()
				.filter(comment -> comment.getStatus() != CommentStatus.Approved)
				.collect(Collectors.toList());
	}

	@Override
	public List<Comment> readCommentsByManifestationIdAndStatus(UUID manifestationId, CommentStatus commentStatus) {
		return readCommentsByManifestationId(manifestationId)
				.stream()
				.filter(comment -> comment.getStatus() == commentStatus)
				.collect(Collectors.toList());
	}



}
