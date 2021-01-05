package servlets.utils.validators;

import java.util.HashMap;

import core.domain.dto.Credidentals;
import core.requests.buyerTypes.CreateBuyerTypeRequest;
import core.requests.buyerTypes.UpdateBuyerTypeRequest;
import core.requests.comments.CreateCommentRequest;
import core.requests.comments.UpdateCommentRequest;
import core.requests.images.Base64ImageForManifestation;
import core.requests.locations.CreateLocationRequest;
import core.requests.locations.UpdateLocationRequest;
import core.requests.users.ChangePasswordRequest;
import core.requests.users.CreateBuyerRequest;
import core.requests.users.CreateSellerRequest;
import core.requests.users.UpdateUserRequest;
import core.servlets.validators.IObjectValidator;
import core.servlets.validators.IValidatorFactory;
import servlets.utils.validators.authorization.CredidentalsValidator;
import servlets.utils.validators.buyerTypes.CreateBuyerTypeRequestValidator;
import servlets.utils.validators.buyerTypes.UpdateBuyerTypeRequestValidator;
import servlets.utils.validators.comments.CreateCommentRequestValidator;
import servlets.utils.validators.comments.UpdateCommentRequestValidator;
import servlets.utils.validators.images.Base64ImageForManifestationValidator;
import servlets.utils.validators.locations.CreateLocationRequestValidator;
import servlets.utils.validators.locations.UpdateLocationRequestValidator;
import servlets.utils.validators.users.ChangePasswordRequestValidator;
import servlets.utils.validators.users.CreateBuyerRequestValidator;
import servlets.utils.validators.users.CreateSellerRequestValidator;
import servlets.utils.validators.users.UpdateUserRequestValidator;

public class ValidatorFactory implements IValidatorFactory {
	private HashMap<Class<?>, Class<?>> validatorsForObjects;

	public ValidatorFactory()
	{
		validatorsForObjects = new HashMap<Class<?>, Class<?>>();
		validatorsForObjects.put(Credidentals.class, CredidentalsValidator.class);
		
		validatorsForObjects.put(CreateBuyerTypeRequest.class, CreateBuyerTypeRequestValidator.class);
		validatorsForObjects.put(UpdateBuyerTypeRequest.class, UpdateBuyerTypeRequestValidator.class);
		
		validatorsForObjects.put(CreateCommentRequest.class, CreateCommentRequestValidator.class);
		validatorsForObjects.put(UpdateCommentRequest.class, UpdateCommentRequestValidator.class);
		
		validatorsForObjects.put(Base64ImageForManifestation.class, Base64ImageForManifestationValidator.class);
		
		validatorsForObjects.put(CreateLocationRequest.class, CreateLocationRequestValidator.class);
		validatorsForObjects.put(UpdateLocationRequest.class, UpdateLocationRequestValidator.class);
		
		validatorsForObjects.put(CreateBuyerRequest.class, CreateBuyerRequestValidator.class);
		validatorsForObjects.put(CreateSellerRequest.class, CreateSellerRequestValidator.class);
		validatorsForObjects.put(UpdateUserRequest.class, UpdateUserRequestValidator.class);
		validatorsForObjects.put(ChangePasswordRequest.class, ChangePasswordRequestValidator.class);
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
