package servlets.utils.validators.authorization;

import core.domain.dto.Credidentals;
import servlets.utils.validators.AbstractObjectValidator;

public class CredidentalsValidator extends AbstractObjectValidator<Credidentals> {
	public CredidentalsValidator(Credidentals validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getUsername()).notNull()
											  .notEmpty()
											  .supplyErrorMessage("Username can not be empty.");
		
		ruleFor(validatedObject.getPassword()).notNull()
											  .notEmpty()
											  .supplyErrorMessage("Password can not be empty.");
	}
}
