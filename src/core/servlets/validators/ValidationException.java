package core.servlets.validators;

import java.util.List;

import core.servlets.exceptions.BadRequestException;

public class ValidationException extends BadRequestException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	
	public ValidationException()
	{
		super();
	}
	
	public ValidationException(String errorMessage)
	{
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public ValidationException(List<String> errorMessages)
	{
		super(errorMessages);
		this.errorMessage = generateJsonArray(errorMessages);
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

}
