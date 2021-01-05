package servlets.utils.validators.tickets;

import java.util.HashMap;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketType;
import servlets.utils.validators.AbstractObjectValidator;

public class TicketOrderValidator extends AbstractObjectValidator<TicketOrder> {
	@SuppressWarnings("unchecked")
	public TicketOrderValidator(TicketOrder validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getManifestationId()).notNull()
													 .supplyErrorMessage("Manifestation id can not be empty.");
		
		ruleFor(validatedObject.getBuyerId()).notNull()
 											 .supplyErrorMessage("Buyer id can not be empty.");
		
		ruleFor(validatedObject.getNumberOfOrderedTicketsMap()).notNull()
															   .must(value -> !((HashMap<TicketType, Integer>)value).isEmpty())
															   .must(value ->
															   {
																   HashMap<TicketType, Integer> numberOfOrderedTicketsMap = (HashMap<TicketType, Integer>) value;
																   
																   return numberOfOrderedTicketsMap.values().stream().allMatch(numberOfTickets -> numberOfTickets != 0);
															   })
															   .supplyErrorMessage("Order must have at least one ticket.");

	}

}
