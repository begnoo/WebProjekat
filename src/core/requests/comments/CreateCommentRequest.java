package core.requests.comments;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import core.domain.enums.CommentStatus;

public class CreateCommentRequest {

	private UUID buyerId;
	private UUID manifestationId;
	private String text;
	private int rating;

	public CreateCommentRequest() {
		super();
	}

	public UUID getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(UUID buyerId) {
		this.buyerId = buyerId;
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
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

}
