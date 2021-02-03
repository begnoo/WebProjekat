package core.responses.users;

import java.time.LocalDateTime;
import java.util.UUID;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class WholeAdministratorObjectResponse extends WholeUserObjectResponseBase {
	
	public WholeAdministratorObjectResponse()
	{
		super();
	}
	
	public WholeAdministratorObjectResponse(UUID id, LocalDateTime createdAt, boolean active, String username, String password,
			String salt, String name, String surname, Gender gender, LocalDateTime birthdate, UserRole role) {
		super(id, createdAt, active, username, password, salt, name, surname, gender, birthdate, role);

	}

}
