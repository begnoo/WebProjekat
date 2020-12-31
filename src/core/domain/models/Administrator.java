package core.domain.models;

import java.time.LocalDateTime;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class Administrator extends User {
	public Administrator()
	{
		super();
	}
	
	public Administrator(String username, String password, String name, String surname,
			Gender gender, LocalDateTime birthdate) {
		super(username, password, name, surname, gender, birthdate, UserRole.Administrator);
	}
}
