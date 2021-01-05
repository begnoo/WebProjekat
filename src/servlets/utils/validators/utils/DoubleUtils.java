package servlets.utils.validators.utils;

public class DoubleUtils {

	public static boolean canParseDouble(Object number)
	{
		try
		{
			Double.parseDouble((String) number);
			
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
