package core.domain.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import core.domain.enums.CommentStatus;

public class Comment extends BaseEntity {
	private UUID buyerId;
	@JsonIgnore
	private Buyer buyer;
	private UUID manifestationId;
	@JsonIgnore
	private Manifestation manifestation;
	private String text;
	private int rating;
	private CommentStatus status;
	
	public Comment()
	{
		super();
	}

	public Comment(UUID buyerId, Buyer buyer, UUID manifestationId, Manifestation manifestation, String text,
			int rating, CommentStatus status) {
		super();
		this.buyerId = buyerId;
		this.buyer = buyer;
		this.manifestationId = manifestationId;
		this.manifestation = manifestation;
		this.text = text;
		this.rating = rating;
		this.status = status;
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
