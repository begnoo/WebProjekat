package servlets.utils.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import core.servlets.validators.IAttributeValidator;
import core.servlets.validators.ValidationException;

public class AttributeValidator implements IAttributeValidator {

	private Object value;
	private String errorMessage;
	private List<Predicate<Object>> predicates;
	
	public AttributeValidator(Object value)
	{
		this.value = value;
		predicates = new ArrayList<Predicate<Object>>();
	}
	
	@Override
	public IAttributeValidator notNull() {
		predicates.add((Predicate<Object>) (value -> value != null));
		
		return this;
	}

	@Override
	public IAttributeValidator notEmpty() {
		predicates.add((Predicate<Object>) (value -> !((String) value).isEmpty()));
		
		return this;
	}

	@Override
	public IAttributeValidator lenght(int minimumCharacters, int maximumCharacters) {
		predicates.add((Predicate<Object>) (value -> minimumCharacters <= ((String) value).length() && ((String) value).length() <= maximumCharacters));
		
		return this;
	}

	@Override
	public IAttributeValidator range(int minimumValue, int maximumValue) {
		predicates.add((Predicate<Object>) (value -> minimumValue <= ((int) value) && ((int) value) <= maximumValue));

		return this;
	}

	@Override
	public IAttributeValidator must(Predicate<Object> predicate) {
		predicates.add(predicate);
		
		return this;
	}

	@Override
	public IAttributeValidator supplyErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		
		return this;
	}

	@Override
	public void validate() throws ValidationException {
		for(Predicate<Object> predicate : predicates)
		{
			boolean isTrue = predicate.test(value);
			
			if(!isTrue)
			{
				throw new ValidationException(errorMessage);
			}
		}
		
	}

}
