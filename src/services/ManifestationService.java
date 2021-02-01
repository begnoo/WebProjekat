package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.CommentStatus;
import core.domain.models.Comment;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.exceptions.BadLogicException;
import core.exceptions.MissingEntityException;
import core.repository.IRepository;
import core.service.ICommentService;
import core.service.IManifestationService;
import core.service.IUserTicketManifestationMediator;

public class ManifestationService extends CrudService<Manifestation> implements IManifestationService {
	private IRepository<Location> locationRepository;
	private ICommentService commentService;
	private IUserTicketManifestationMediator mediator;
	
	public ManifestationService(IRepository<Manifestation> repository, IRepository<Location> locationRepository, ICommentService commentService, IUserTicketManifestationMediator mediator) {
		super(repository);
		this.locationRepository = locationRepository;
		this.commentService = commentService;
		this.mediator = mediator;
	}

	@Override
	public List<Manifestation> readOrderedByDescendingDate() {
		return repository.read()
				.stream()
				.sorted((manifestation1, manifestation2) -> manifestation2.getEventDate().compareTo(manifestation1.getEventDate()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Manifestation> readByLocationId(UUID locationId) {
		return repository.read()
				.stream()
				.filter(manifestation -> manifestation.getLocationId().equals(locationId))
				.collect(Collectors.toList());
	}

	@Override
	public Manifestation create(Manifestation manifestation) {
				
		if(!checkIfLocationExists(manifestation.getLocationId())) {
			throw new MissingEntityException(String.format("Location with id = %s does not exists.", manifestation.getLocationId()));
		}
		
		List<Manifestation> manifestationsWithSameLocationAndOverlapingEventDate = readWithSameLocationAndOverlapingEventDate(manifestation);
		if (!manifestationsWithSameLocationAndOverlapingEventDate.isEmpty()) {
			throw new BadLogicException("There is already manifestation on given location at that time.");
		}
		
		manifestation.setImagePath("../WebProjekat/rest/images/default.jpg");
		manifestation.setStatus(false);
		return repository.create(manifestation);
	}

	@Override
	public Manifestation update(Manifestation manifestationForUpdate) {
		if(!checkIfLocationExists(manifestationForUpdate.getLocationId())) {
			throw new MissingEntityException(String.format("Location with id = %s does not exists.", manifestationForUpdate.getLocationId()));
		}

		List<Manifestation> manifestationsWithSameLocationAndOverlapingEventDateWithoutThisOne = readWithSameLocationAndOverlapingEventDate(manifestationForUpdate)
				.stream()
				.filter(manifestation -> manifestation.getId() != manifestationForUpdate.getId())
				.collect(Collectors.toList());
		
		if (!manifestationsWithSameLocationAndOverlapingEventDateWithoutThisOne.isEmpty()) {
			throw new BadLogicException("There is already manifestation on given location at that time.");
		}
		
		return repository.update(manifestationForUpdate);
	}
	
	private boolean checkIfLocationExists(UUID locationId) {
		return locationRepository.read(locationId) != null;
	}
	
	
	private List<Manifestation> readWithSameLocationAndOverlapingEventDate(Manifestation queryManifestation) {
		return repository.read()
				.stream()
				.filter(manifestation -> queryManifestation.getLocationId().equals(manifestation.getLocationId()))
				.filter(manifestation -> checkIfOverlapingManifestationDate(queryManifestation, manifestation))
				.collect(Collectors.toList());
	}
	
	private boolean checkIfOverlapingManifestationDate(Manifestation firstManifestation, Manifestation secondManifestation) {
		LocalDateTime firstManifestationStart = firstManifestation.getEventDate();
		LocalDateTime firstManifestationEnd = firstManifestation.getEventEndDate();
	
		LocalDateTime secondManifestationStart = secondManifestation.getEventDate();
		LocalDateTime secondManifestationEnd = secondManifestation.getEventEndDate();
		
		boolean firstManifestationStartNotOverlaping = firstManifestationStart.compareTo(secondManifestationEnd) >= 0;
		boolean secondManifestationStartNotOverlaping = secondManifestationStart.compareTo(firstManifestationEnd) >= 0;
		
		return !firstManifestationStartNotOverlaping && !secondManifestationStartNotOverlaping;
	}

	@Override
	public Manifestation delete(UUID manifestationId)
	{
		Manifestation manifestation = repository.read(manifestationId);
		if(manifestation == null) {
			throw new MissingEntityException(String.format("Manifestation with id = %s does not exists.", manifestationId));
		}
		
		List<Ticket> manifestationTickets = mediator.readTicketsByManifestaionId(manifestationId);
		for(Ticket ticket : manifestationTickets) {
			mediator.deleteTicket(ticket.getId());
		}
		
		List<Comment> manifestationComments = commentService.readByManifestationId(manifestationId);
		for(Comment comment : manifestationComments) {
			commentService.delete(comment.getId());
		}
		
		return super.delete(manifestationId);
	}
	
	@Override
	public Manifestation updateNumberOfSeats(Manifestation manifestation, int additionalSeats) {
		int newNumberOfManifestationSeats = manifestation.getSeats() + additionalSeats;
		if(newNumberOfManifestationSeats < 0) {
			return null;
		}
		
		manifestation.setSeats(newNumberOfManifestationSeats);
		
		return repository.update(manifestation);
	}

	@Override
	public int getRating(UUID manifestationId) {
		List<Comment> commentsForManifestation = commentService.readByManifestationId(manifestationId);
		double rating = commentsForManifestation.stream()
												.filter(comment -> comment.getStatus() == CommentStatus.Approved)
												.map(comment -> comment.getRating())
												.collect(Collectors.averagingInt(Integer::intValue));
		
		return (int) Math.round(rating);
	}
}