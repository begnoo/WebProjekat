package core.servlets.validators;

public interface IObjectValidator<T> {
	
	IAttributeValidator ruleFor(Object attributeValue);
	
	void validate() throws ValidationException;
}
