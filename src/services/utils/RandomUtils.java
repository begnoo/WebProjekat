package services.utils;

import java.util.Random;

public class RandomUtils {

	public static String getRandomString(int lenght) {
	    int leftLimit = 97;
	    int rightLimit = 122;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(lenght)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    return generatedString;

	}
}
