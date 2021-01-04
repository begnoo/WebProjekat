package core.servlets.validators;

import java.util.function.Predicate;

public interface IAttributeValidator {
	IAttributeValidator notNull();
	
	IAttributeValidator notEmpty();
	
	IAttributeValidator lenght(int minimumCharacters, int maximumCharacters);
	
	IAttributeValidator range(int minimumValue, int maximumValue);
	
	IAttributeValidator must(Predicate<Object> predicate);
	
	IAttributeValidator supplyErrorMessage(String errorMessage);
	
	void validate() throws ValidationException;
}
