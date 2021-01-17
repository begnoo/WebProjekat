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
													   sortBy.equals("surname") ||
													   sortBy.equals("username");
											})
											.supplyErrorMessage("Sort by must be one of following strings: name, surname, username or empty string.");

		ruleFor(validatedObject.getOrderBy()).notNull()
					 						 .supplyErrorMessage("Order by can not be null.");
		
		ruleFor(validatedObject).must(value ->
								{
									UsersSearchParamethers searchParamethers = (UsersSearchParamethers)value; 
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
