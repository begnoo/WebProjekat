package servlets.utils.validators.buyerTypes;

import core.requests.buyerTypes.CreateBuyerTypeRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class CreateBuyerTypeRequestValidator extends AbstractObjectValidator<CreateBuyerTypeRequest> {
	public CreateBuyerTypeRequestValidator(CreateBuyerTypeRequest validatedObject)
	{
		super(validatedObject);
		
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
