package servlets.utils.validators.search;

import core.domain.dto.ManifestationsSearchParamethers;
import servlets.utils.validators.AbstractObjectValidator;

public class ManifestationsSearchParamethersValidator extends AbstractObjectValidator<ManifestationsSearchParamethers> {
	public ManifestationsSearchParamethersValidator(ManifestationsSearchParamethers validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getName()).notNull()
										  .supplyErrorMessage("Manifestation name can not be null.");
		
		ruleFor(validatedObject.getCity()).notNull()
										  .supplyErrorMessage("City can not be null.");
		
		ruleFor(validatedObject.getType()).notNull()
							   			  .supplyErrorMessage("Manifestation type can not be null.");
		
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
													   sortBy.equals("name") ||
													   sortBy.equals("eventDate") ||
													   sortBy.equals("regularTicketPrice") ||
													   sortBy.equals("location");
											})
											.supplyErrorMessage("Sort by must be one of following strings: name, eventDate, regularTicketPrice, location or empty string.");
		
		ruleFor(validatedObject.getOrderBy()).notNull()
											 .supplyErrorMessage("Order by can not be null.");
		
		ruleFor(validatedObject).must(value ->
								{
									ManifestationsSearchParamethers searchParamethers = (ManifestationsSearchParamethers)value; 
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
