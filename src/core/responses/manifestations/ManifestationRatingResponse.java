package core.responses.manifestations;

import java.util.UUID;

public class ManifestationRatingResponse {
	private UUID manifestationId;
	private int rating;

	public ManifestationRatingResponse() {
		super();
	}

	public ManifestationRatingResponse(UUID manifestationId, int rating) {
		super();
		this.manifestationId = manifestationId;
		this.rating = rating;
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public int getRating() {
		return rating;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
