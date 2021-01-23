package core.exceptions;

public class BadLogicException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BadLogicException() {
		
	}
	
	public BadLogicException(String message) {
		super(message);
	}

}
