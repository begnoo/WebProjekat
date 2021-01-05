package servlets.utils.validators.manifestations;

import java.time.LocalDateTime;

import core.requests.manifestations.CreateManifestationRequest;
import servlets.utils.validators.AbstractObjectValidator;

public class CreateManifestationRequestValidator extends AbstractObjectValidator<CreateManifestationRequest> {
	public CreateManifestationRequestValidator(CreateManifestationRequest validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getName()).notNull()
										  .notEmpty()
										  .lenght(3, 150)
										  .supplyErrorMessage("Name must be between 3 and 150 characters long.");
		
		ruleFor(validatedObject.getType()).notNull()
										  .supplyErrorMessage("Type can not be empty.");
		
		
		ruleFor(validatedObject.getSeats()).notNull()
										   .range(1, Integer.MAX_VALUE)
										   .supplyErrorMessage("There must be at least one seat.");
		
		ruleFor(validatedObject.getEventDate()).notNull()
											   .supplyErrorMessage("Start date can not be empty.");
		
		ruleFor(validatedObject.getEventDate()).must(value ->
												{
													if(value == null)
													{
														return true;
													}
													
													return ((LocalDateTime)value).compareTo(LocalDateTime.now()) > 0;
												})
											   .supplyErrorMessage("Date already passed.");
		
		ruleFor(validatedObject.getEventEndDate()).notNull()
		   									      .supplyErrorMessage("End date can not be empty.");
		
		ruleFor(validatedObject).must(value -> {
									CreateManifestationRequest request = (CreateManifestationRequest) value;
									
									if(request.getEventDate() == null || request.getEventEndDate() == null)
									{
										return true;
									}
									
									return request.getEventDate().compareTo(request.getEventEndDate()) < 0;
								})
								.supplyErrorMessage("End date must be after start date.");
		
		ruleFor(validatedObject.getRegularTicketPrice()).notNull()
														.range(0, Integer.MAX_VALUE)
														.supplyErrorMessage("Price must be positive number.");
		
		ruleFor(validatedObject.getLocationId()).notNull()
												.supplyErrorMessage("Location id can not be empty.");
		
		ruleFor(validatedObject.getSellerId()).notNull()
											  .supplyErrorMessage("Seller id can not be empty.");
	}
}
