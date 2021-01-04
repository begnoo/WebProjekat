package servlets;

import core.domain.models.Address;
import core.requests.locations.CreateAddressRequest;
import core.requests.locations.UpdateAddressRequest;
import core.servlets.mappers.IMapper;
import core.servlets.validators.IObjectValidator;
import core.servlets.validators.IValidatorFactory;
import servlets.utils.mapper.objects.ObjectMapper;
import servlets.utils.validators.ValidatorFactory;

public abstract class AbstractServletBase {
	protected IValidatorFactory validatorFactory;
	protected IMapper mapper;

	public AbstractServletBase()
	{
		validatorFactory = new ValidatorFactory();
		mapper = new ObjectMapper();
		mapper.addNestedMapping(CreateAddressRequest.class, Address.class);
		mapper.addNestedMapping(UpdateAddressRequest.class, Address.class);
	}
	
	protected void validateRequest(Object request)
	{
		IObjectValidator<?> validator = validatorFactory.getValidator(request);
		
		validator.validate();
	}
}
