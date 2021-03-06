package core.domain.models;

import java.time.LocalDateTime;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class Administrator extends User {
	public Administrator()
	{
		super();
		this.setRole(UserRole.Administrator);
	}
	
	public Administrator(String username, String password, String salt, String name, String surname,
			Gender gender, LocalDateTime birthdate) {
		super(username, password, salt, name, surname, gender, birthdate, UserRole.Administrator);
	}
}
