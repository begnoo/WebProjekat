package core.domain.models;

import java.util.UUID;

public class Comment extends BaseEntity {
	private UUID buyerId;
	private Buyer buyer;
	private UUID manifestationId;
	private Manifestation manifestation;
	private String text;
	private int rating;
	
	public Comment()
	{
		super();
	}

	public Comment(UUID buyerId, Buyer buyer, UUID manifestationId, Manifestation manifestation, String text,
			int rating) {
		super();
		this.buyerId = buyerId;
		this.buyer = buyer;
		this.manifestationId = manifestationId;
		this.manifestation = manifestation;
		this.text = text;
		this.rating = rating;
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
}
