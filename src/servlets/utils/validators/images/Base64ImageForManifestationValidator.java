package servlets.utils.validators.images;

import core.requests.images.Base64ImageForManifestation;
import servlets.utils.validators.AbstractObjectValidator;

public class Base64ImageForManifestationValidator extends AbstractObjectValidator<Base64ImageForManifestation> {
	public Base64ImageForManifestationValidator(Base64ImageForManifestation validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getManifestationId()).notNull()
													 .supplyErrorMessage("Manifestation id can not be empty.");
		
		ruleFor(validatedObject.getBase64Representation()).notNull()
														  .notEmpty()
														  .supplyErrorMessage("Base 64 string representation of image can not be empty.");


	}
}
