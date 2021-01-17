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
	}
}
