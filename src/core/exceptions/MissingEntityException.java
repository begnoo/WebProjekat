package core.exceptions;

public class MissingEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MissingEntityException() {
		
	}
	
	public MissingEntityException(String message) {
		super(message);
	}

}
