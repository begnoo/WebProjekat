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
import core.exceptions.BadLogicException;
import core.exceptions.MissingEntityException;
import core.repository.IRepository;
import core.service.ICommentService;

public class CommentService extends CrudService<Comment> implements ICommentService {
	private IRepository<Manifestation> manifestationRepository;
	private IRepository<User> userRepository;

	public CommentService(IRepository<Comment> repository, IRepository<Manifestation> manifestationRepository,
			IRepository<User> userRepository) {
		super(repository);
		this.manifestationRepository = manifestationRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Comment create(Comment comment) {
		Manifestation manifestation = manifestationRepository.read(comment.getManifestationId());
		if(manifestation == null) {
			throw new MissingEntityException(String.format("Manifestation with id = %s does not exists.", comment.getManifestationId()));
		}
		Buyer buyer = (Buyer) userRepository.read(comment.getBuyerId());
		if(buyer == null) {
			throw new MissingEntityException(String.format("Buyer with id = %s does not exists.", comment.getBuyerId()));
		}
		
		List<Ticket> buyersReservedTickets = buyer.getTickets().stream()
				.filter(ticket -> ticket.getManifestationId().equals(comment.getManifestationId()))
				.filter(ticket -> ticket.getStatus() == TicketStatus.Reserved)
				.collect(Collectors.toList());
		
		if (manifestation.getEventEndDate().isAfter(LocalDateTime.now()) || buyersReservedTickets.isEmpty()) {
			throw new BadLogicException("Buyer can not comment this manifestation.");
		}

		comment.setStatus(CommentStatus.Pending);

		return repository.create(comment);
	}
	
	@Override
	public Comment update(Comment comment) {
		comment.setStatus(CommentStatus.Pending);

		return super.update(comment);
	}

	@Override
	public List<Comment> readByBuyerId(UUID buyerId) {
		return repository.read().stream()
				.filter(comment -> comment.getBuyerId().equals(buyerId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Comment> readByManifestationId(UUID manifestationId) {
		return repository.read().stream()
				.filter(comment -> comment.getManifestationId().equals(manifestationId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Comment> readByManifestationIdAndStatus(UUID manifestationId, CommentStatus commentStatus) {
		return readByManifestationId(manifestationId).stream()
				.filter(comment -> comment.getStatus() == commentStatus)
				.collect(Collectors.toList());
	}

	@Override
	public Comment changeStatus(UUID commentId, CommentStatus commentStatus) {
		Comment comment = repository.read(commentId);
		if(comment == null) {
			throw new MissingEntityException(String.format("Comment with id = %s does not exists.", commentId));
		}
		
		if(comment.getStatus() != CommentStatus.Pending) {
			throw new BadLogicException("You can not change status of this comment.");
		}
		
		if(commentStatus == CommentStatus.Pending) {
			throw new BadLogicException("You can not change status of comment to Pending.");
		}
		
		comment.setStatus(commentStatus);
		return super.update(comment);
	}

}
