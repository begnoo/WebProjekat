package core.responses.users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;
import core.domain.models.Manifestation;

public class WholeSellerObjectResponse extends WholeUserObjectResponseBase {
	List<Manifestation> manifestations;

	public WholeSellerObjectResponse()
	{
		super();
	}
	
	public WholeSellerObjectResponse(UUID id, LocalDateTime createdAt, boolean active, String username, String password,
			String salt, String name, String surname, Gender gender, LocalDateTime birthdate, UserRole role, List<Manifestation> manifestations) {
		super(id, createdAt, active, username, password, salt, name, surname, gender, birthdate, role);

		this.manifestations = manifestations;
	}

	public List<Manifestation> getManifestations() {
		return manifestations;
	}

	public void setManifestations(List<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
}
