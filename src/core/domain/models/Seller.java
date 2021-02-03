package core.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class Seller extends User {
	@JsonIgnore
	List<Manifestation> manifestations;
	
	public Seller()
	{
		super();
		this.manifestations = new ArrayList<Manifestation>();
		this.setRole(UserRole.Seller);
	}
	
	public Seller(String username, String password, String salt, String name, String surname,
			Gender gender, LocalDateTime birthdate, List<Manifestation> manifestations) {
		super(username, password, salt, name, surname, gender, birthdate, UserRole.Seller);
		this.manifestations = manifestations;
	}

	public List<Manifestation> getManifestations() {
		return manifestations;
	}

	public void setManifestations(List<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
}

