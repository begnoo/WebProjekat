package services.utils;

public class BytesUtils {
	public static String toString(byte[] bytes) {
	    StringBuilder hexString = new StringBuilder(2 * bytes.length);
	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(0xff & bytes[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    
	    return hexString.toString();
	}
}
