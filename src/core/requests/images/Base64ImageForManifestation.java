package core.requests.images;

import java.util.UUID;

public class Base64ImageForManifestation {
	private UUID manifestationId;
	private String base64Representation;
	private String extension;

	public Base64ImageForManifestation() {
		super();
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
	}

	public String getBase64Representation() {
		return base64Representation;
	}

	public void setBase64Representation(String base64Representation) {
		this.base64Representation = base64Representation;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
