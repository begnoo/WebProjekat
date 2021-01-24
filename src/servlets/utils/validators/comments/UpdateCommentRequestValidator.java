package servlets.utils.validators.comments;

import core.requests.comments.UpdateCommentRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class UpdateCommentRequestValidator extends AbstractObjectValidator<UpdateCommentRequest> { 
	public UpdateCommentRequestValidator(UpdateCommentRequest validatedObject) {
		super(validatedObject);
		
		ruleFor(validatedObject.getId()).notNull()
										.supplyErrorMessage("Id can not be empty.");
		
		ruleFor(validatedObject.getText()).notNull()
										  .notEmpty()
										  .lenght(10, 1000)
										  .supplyErrorMessage("Comment must be between 10 and 1000 characters long.");
		
		ruleFor(validatedObject.getRating()).notNull()
											.range(1, 5)
											.supplyErrorMessage("Rating must be between 1 and 5.");
	}
}
