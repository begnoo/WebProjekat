package services;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import core.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtService implements IJwtService {
	private static final String SECRET = "READ THIS; THIS IS NOT A REAL SECRET; CHANGE WITH REAL ONE HERE!!!";
	private static final long ONE_MINUTE_IN_MILLIS = 60000;

	public JwtService()
	{
		
	}
	
	@Override
	public String generateJwtTokenForUser(UUID userId) {
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
	    
	    @SuppressWarnings("deprecation")
		JwtBuilder builder = Jwts.builder()
    							 .setSubject(userId.toString())
					             .setIssuedAt(new Date())
					             .setExpiration(new Date(new Date().getTime() + 30 * ONE_MINUTE_IN_MILLIS))
						         .signWith(SignatureAlgorithm.HS256, apiKeySecretBytes);
	  
	    return builder.compact();
	}

	@Override
	public UUID getUserIdDromJwtToken(String jwtToken) {
		try
		{
			Claims claims = Jwts.parserBuilder()
		            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
		            .build()
		            .parseClaimsJws(jwtToken)
		            .getBody();

			return UUID.fromString(claims.getSubject());
		}
		catch(Exception e) {
			System.out.println("Bad jwt token");
		}
		
		return new UUID(0, 0);
	}
}
