package servlets.utils.validators.search;

import core.domain.dto.TicketsSearchParamethers;
import servlets.utils.validators.AbstractObjectValidator;

public class TicketsSearchParamethersValidator extends AbstractObjectValidator<TicketsSearchParamethers>{
	public TicketsSearchParamethersValidator(TicketsSearchParamethers validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getBuyerId()).notNull()
											 .supplyErrorMessage("Buyer id can not be empty.");
		
		ruleFor(validatedObject.getManifestationName()).notNull()
													   .supplyErrorMessage("Manifestation name can not be null.");
	}
}
