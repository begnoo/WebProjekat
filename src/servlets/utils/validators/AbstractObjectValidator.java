package servlets.utils.validators;

import java.util.ArrayList;
import java.util.List;

import core.servlets.validators.IAttributeValidator;
import core.servlets.validators.IObjectValidator;
import core.servlets.validators.ValidationException;

public class AbstractObjectValidator<T> implements IObjectValidator<T> {

	protected T validatedObject;
	private List<IAttributeValidator> attributeValidators;
	
	public AbstractObjectValidator(T validatedObject)
	{
		super();
		attributeValidators = new ArrayList<IAttributeValidator>();
	}
	
	@Override
	public IAttributeValidator ruleFor(Object attributeValue) {
		IAttributeValidator attributeValidator = new AttributeValidator(attributeValue);
		attributeValidators.add(attributeValidator);
		
		return attributeValidator;
	}

	@Override
	public void validate() throws ValidationException {
		List<String> errorMessages = new ArrayList<String>();
		
		for(IAttributeValidator attributeValidator : attributeValidators)
		{
			try
			{
				attributeValidator.validate();
			} catch(ValidationException e) {
				errorMessages.add(e.getErrorMessage());
			}
		}
		
		if(!errorMessages.isEmpty()) {
			throw new ValidationException(errorMessages);
		}
	}
}
