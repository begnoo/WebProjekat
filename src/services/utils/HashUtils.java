package services.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
	public static String getSaltedAndHashedPassword(String password, String salt) {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String saltedPassword = password + salt;
			byte[] encodedSaltedPassword = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
			
			return BytesUtils.toString(encodedSaltedPassword);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
}
