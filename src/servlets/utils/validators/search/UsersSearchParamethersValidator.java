package servlets.utils.validators.search;

import core.domain.dto.UsersSearchParamethers;
import servlets.utils.validators.AbstractObjectValidator;

public class UsersSearchParamethersValidator extends AbstractObjectValidator<UsersSearchParamethers> {
	public UsersSearchParamethersValidator(UsersSearchParamethers validatedObject) {
		super(validatedObject);
		
		ruleFor(validatedObject.getName()).notNull()
										  .supplyErrorMessage("Name can not be null.");
		ruleFor(validatedObject.getSurname()).notNull()
		  									 .supplyErrorMessage("Surname can not be null.");
		
		ruleFor(validatedObject.getUsername()).notNull()
		  									  .supplyErrorMessage("Username can not be null.");
		
		ruleFor(validatedObject.getRole()).notNull()
										  .supplyErrorMessage("Role can not be null.");
		
	}
}
