package servlets.utils.validators;

import core.requests.users.CreateBuyerRequest;

public class CreateBuyerRequestValidator extends AbstractObjectValidator<CreateBuyerRequest> {

	public CreateBuyerRequestValidator(CreateBuyerRequest validatedObject) {
		super(validatedObject);
		
		super.ruleFor(validatedObject.getName()).notNull()
												.notEmpty()
												.lenght(3, 30)
												.supplyErrorMessage("Name must have between 3 and 30 characters.");
		
		super.ruleFor(validatedObject.getSurname()).notNull()
												   .notEmpty()
												   .supplyErrorMessage("Surname can not be empty.");
		
		super.ruleFor(validatedObject.getSurname()).must(value -> { String x = (String)value; return Character.toUpperCase(x.charAt(0)) == x.charAt(0); })
												.supplyErrorMessage("Surname must be upper.");
	}

}
