package servlets.utils.validators.locations;

import core.requests.locations.CreateLocationRequest;
import servlets.utils.validators.AbstractObjectValidator;
import servlets.utils.validators.utils.DoubleUtils;
 
public class CreateLocationRequestValidator extends AbstractObjectValidator<CreateLocationRequest> {
	public CreateLocationRequestValidator(CreateLocationRequest validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getLatitude()).notNull()
											  .notEmpty()
											  .must(value -> DoubleUtils.canParseDouble(value))
											  .supplyErrorMessage("Latitude must be a number.");
		
		ruleFor(validatedObject.getLongitude()).notNull()
											   .notEmpty()
											   .must(value -> DoubleUtils.canParseDouble(value))
											   .supplyErrorMessage("Longitude must be a number.");
		
		ruleFor(validatedObject.getAddress().getHouseNumber()).notNull()
															  .notEmpty()
															  .lenght(1, 15)
															  .supplyErrorMessage("House number must be between 1 and 15 characters long.");
		
		ruleFor(validatedObject.getAddress().getPlace()).notNull()
														.notEmpty()
														.lenght(1, 50)
														.supplyErrorMessage("Place must be between 1 and 50 characters long.");
		
		ruleFor(validatedObject.getAddress().getPostalCode()).notNull()
															 .notEmpty()
															 .lenght(1, 15)
															 .supplyErrorMessage("Postal code must be between 1 and 15 characters long.");
		
		ruleFor(validatedObject.getAddress().getStreet()).notNull()
														 .notEmpty()
														 .lenght(1, 50)
														 .supplyErrorMessage("Street must be between 1 and 50 characters long.");


	}
}