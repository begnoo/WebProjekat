package servlets.utils.validators;

import java.util.HashMap;

import core.domain.dto.Credidentals;
import core.requests.buyerTypes.CreateBuyerTypeRequest;
import core.requests.buyerTypes.UpdateBuyerTypeRequest;
import core.requests.comments.CreateCommentRequest;
import core.requests.comments.UpdateCommentRequest;
import core.requests.users.CreateBuyerRequest;
import core.servlets.validators.IObjectValidator;
import core.servlets.validators.IValidatorFactory;
import servlets.utils.validators.authorization.CredidentalsValidator;
import servlets.utils.validators.buyerTypes.CreateBuyerTypeRequestValidator;
import servlets.utils.validators.buyerTypes.UpdateBuyerTypeRequestValidator;
import servlets.utils.validators.comments.CreateCommentRequestValidator;
import servlets.utils.validators.comments.UpdateCommentRequestValidator;

public class ValidatorFactory implements IValidatorFactory {
	private HashMap<Class<?>, Class<?>> validatorsForObjects;

	public ValidatorFactory()
	{
		validatorsForObjects = new HashMap<Class<?>, Class<?>>();
		validatorsForObjects.put(CreateBuyerRequest.class, CreateBuyerRequestValidator.class);
		validatorsForObjects.put(Credidentals.class, CredidentalsValidator.class);
		validatorsForObjects.put(CreateBuyerTypeRequest.class, CreateBuyerTypeRequestValidator.class);
		validatorsForObjects.put(UpdateBuyerTypeRequest.class, UpdateBuyerTypeRequestValidator.class);
		validatorsForObjects.put(CreateCommentRequest.class, CreateCommentRequestValidator.class);
		validatorsForObjects.put(UpdateCommentRequest.class, UpdateCommentRequestValidator.class);
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
