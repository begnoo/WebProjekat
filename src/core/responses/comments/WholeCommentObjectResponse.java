package core.responses.comments;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.CommentStatus;
import core.domain.models.Buyer;
import core.domain.models.Manifestation;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class WholeCommentObjectResponse {
	private UUID id;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime createdAt;
	private boolean active;
	private UUID buyerId;
	private Buyer buyer;
	private UUID manifestationId;
	private Manifestation manifestation;
	private String text;
	private int rating;
	private CommentStatus status;

	public WholeCommentObjectResponse() {
		super();
	}

	public WholeCommentObjectResponse(UUID id, LocalDateTime createdAt, boolean active, UUID buyerId, Buyer buyer,
			UUID manifestationId, Manifestation manifestation, String text, int rating, CommentStatus status) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.active = active;
		this.buyerId = buyerId;
		this.buyer = buyer;
		this.manifestationId = manifestationId;
		this.manifestation = manifestation;
		this.text = text;
		this.rating = rating;
		this.status = status;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public UUID getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(UUID buyerId) {
		this.buyerId = buyerId;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public CommentStatus getStatus() {
		return status;
	}

	public void setStatus(CommentStatus status) {
		this.status = status;
	}

}
