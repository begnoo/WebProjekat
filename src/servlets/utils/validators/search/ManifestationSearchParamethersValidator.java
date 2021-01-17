package servlets.utils.validators.search;

import core.domain.dto.ManifestationsSearchParamethers;
import servlets.utils.validators.AbstractObjectValidator;

public class ManifestationSearchParamethersValidator extends AbstractObjectValidator<ManifestationsSearchParamethers> {
	public ManifestationSearchParamethersValidator(ManifestationsSearchParamethers validatedObject)
	{
		super(validatedObject);
		
		ruleFor(validatedObject.getName()).notNull();
		
		ruleFor(validatedObject.getCity()).notNull();
	}
}
