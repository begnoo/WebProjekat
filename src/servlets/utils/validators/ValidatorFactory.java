package servlets.utils.validators;

import java.util.HashMap;

import core.requests.users.CreateBuyerRequest;
import core.servlets.validators.IObjectValidator;
import core.servlets.validators.IValidatorFactory;

public class ValidatorFactory implements IValidatorFactory {
	private HashMap<Class<?>, Class<?>> validatorsForObjects;

	public ValidatorFactory()
	{
		validatorsForObjects = new HashMap<Class<?>, Class<?>>();
		validatorsForObjects.put(CreateBuyerRequest.class, CreateBuyerRequestValidator.class);
	}
	
	@Override
	public IObjectValidator<?> getValidator(Object validatedObject) {
		Class<?> classOfValidatedObject = validatedObject.getClass();
		Class<?> classOfValidator = validatorsForObjects.get(classOfValidatedObject);
		
		IObjectValidator<?> validatorObject = null;
		try {
			validatorObject = (IObjectValidator<?>) classOfValidator.getConstructor(validatedObject.getClass())
																	.newInstance(validatedObject);
		} catch (Exception e) {
			System.out.println("Error while trying to instantiate validator for: " + classOfValidatedObject.getSimpleName());
		} 
		
		return validatorObject;
	}
	

}
