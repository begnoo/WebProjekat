package core.service;

import java.util.UUID;

public interface IJwtService {
	String generateJwtTokenForUser(UUID userId);
	
	UUID getUserIdDromJwtToken(String jwtToken);
}
