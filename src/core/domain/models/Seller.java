package core.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class Seller extends User {
	List<Manifestation> manifestations;
	
	public Seller()
	{
		super();
		this.manifestations = new ArrayList<Manifestation>(); 
	}
	
	public Seller(String username, String password, String name, String surname,
			Gender gender, LocalDateTime birthdate, List<Manifestation> manifestations) {
		super(username, password, name, surname, gender, birthdate, UserRole.Seller);
		this.manifestations = manifestations;
	}

	public List<Manifestation> getManifestations() {
		return manifestations;
	}

	public void setManifestations(List<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
}

