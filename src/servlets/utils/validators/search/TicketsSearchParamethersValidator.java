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
		
		ruleFor(validatedObject.getType()).notNull()
										  .supplyErrorMessage("Ticket type can not be null.");
		
		ruleFor(validatedObject.getStatus()).notNull()
		  									.supplyErrorMessage("Ticket status can not be null.");
		
		ruleFor(validatedObject.getSortBy()).notNull()
											.supplyErrorMessage("Sorting attribute can not be null.");

		ruleFor(validatedObject.getSortBy()).must(value ->
											{
												if(value == null)
												{
													return true;
												}
												
												String sortBy = (String)value;
												return sortBy.isBlank() ||
													   sortBy.equals("manifestation") ||
													   sortBy.equals("price") ||
													   sortBy.equals("manifestationDate");
											})
											.supplyErrorMessage("Sort by must be one of following strings: manifestation, price, manifestationDate or empty string.");

		ruleFor(validatedObject.getOrderBy()).notNull()
			 								 .supplyErrorMessage("Order by can not be null.");

		ruleFor(validatedObject).must(value ->
								{
									TicketsSearchParamethers searchParamethers = (TicketsSearchParamethers)value; 
									if(searchParamethers.getSortBy() == null || searchParamethers.getSortBy().isBlank() || searchParamethers.getOrderBy() == null)
									{
										return true;
									}
									
									String orderBy = searchParamethers.getOrderBy();
									
									return orderBy.equals("Ascending") || orderBy.equals("Descending");
								})
								.supplyErrorMessage("Order by must be Ascending or Descending.");
	}
}

