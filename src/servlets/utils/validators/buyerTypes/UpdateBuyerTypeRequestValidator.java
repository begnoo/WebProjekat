package servlets.utils.validators.buyerTypes;

import core.requests.buyerTypes.UpdateBuyerTypeRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class UpdateBuyerTypeRequestValidator extends AbstractObjectValidator<UpdateBuyerTypeRequest> {
	public UpdateBuyerTypeRequestValidator(UpdateBuyerTypeRequest validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getId()).notNull()
										.supplyErrorMessage("Id can not be empty.");
		
		ruleFor(validatedObject.getName()).notNull()
										  .lenght(3, 30)
										  .supplyErrorMessage("Name must be between 3 and 30 characters long.");
		
		ruleFor(validatedObject.getDiscount()).notNull()
											  .range(0, 50)
											  .supplyErrorMessage("Discount must be between 1 and 50%.");
		
		ruleFor(validatedObject.getMinimumPoints()).notNull()
												   .range(0, Integer.MAX_VALUE)
												   .supplyErrorMessage("Minimum points must be positive number.");
	}
}
