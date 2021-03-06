package servlets.utils.validators.users;

import java.time.LocalDateTime;

import core.requests.users.CreateBuyerRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class CreateBuyerRequestValidator extends AbstractObjectValidator<CreateBuyerRequest> {
	public CreateBuyerRequestValidator(CreateBuyerRequest validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getUsername()).notNull()
											  .notEmpty()
											  .lenght(3, 30)
											  .supplyErrorMessage("Username must be between 3 and 30 characters long.");
		
		ruleFor(validatedObject.getPassword()).notNull()
											  .notEmpty()
											  .lenght(8, 30)
											  .supplyErrorMessage("Password must be between 8 and 30 characters long.");
		
		ruleFor(validatedObject.getName()).notNull()
										  .notEmpty()
										  .lenght(2, 30)
										  .supplyErrorMessage("Name must be between 2 and 30 characters long.");
		
		ruleFor(validatedObject.getSurname()).notNull()
											 .notEmpty()
											 .lenght(2, 30)
											 .supplyErrorMessage("Surname must be between 2 and 30 characters long.");
		
		ruleFor(validatedObject.getGender()).notNull()
											.supplyErrorMessage("Gender can not be empty.");

		ruleFor(validatedObject.getBirthdate()).notNull()
											   .must(value -> {
												   LocalDateTime birthdate = (LocalDateTime) value;
												   return birthdate.compareTo(LocalDateTime.now().minusYears(13)) < 0;
											   })
											   .supplyErrorMessage("User must be at least 13 years old.");
	}

}
