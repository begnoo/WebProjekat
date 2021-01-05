package servlets.utils.validators.users;

import core.requests.users.ChangePasswordRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class ChangePasswordRequestValidator extends AbstractObjectValidator<ChangePasswordRequest> {
	public ChangePasswordRequestValidator(ChangePasswordRequest validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getCurrentPassword()).notNull()
													 .notEmpty()
													 .lenght(8, 30)
													 .supplyErrorMessage("Current password must be between 8 and 30 characters long.");

		ruleFor(validatedObject.getNewPassword()).notNull()
												 .notEmpty()
												 .lenght(8, 30)
												 .supplyErrorMessage("New password must be between 8 and 30 characters long.");

	}
}
