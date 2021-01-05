package servlets.utils.validators.comments;

import core.requests.comments.CreateCommentRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class CreateCommentRequestValidator extends AbstractObjectValidator<CreateCommentRequest> { 
	public CreateCommentRequestValidator(CreateCommentRequest validatedObject) {
		super(validatedObject);
		
		ruleFor(validatedObject.getBuyerId()).notNull()
											 .supplyErrorMessage("Buyer id can not be empty.");
	
		ruleFor(validatedObject.getManifestationId()).notNull()
		 									 		 .supplyErrorMessage("Manifestation id can not be empty.");
		
		ruleFor(validatedObject.getText()).notNull()
										  .notEmpty()
										  .lenght(10, 1000)
										  .supplyErrorMessage("Comment must be between 10 and 1000 characters long.");
		
		ruleFor(validatedObject.getRating()).notNull()
											.range(1, 5)
											.supplyErrorMessage("Rating must be between 1 and 5.");

	}
}
