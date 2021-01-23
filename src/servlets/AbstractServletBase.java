package servlets;

import core.domain.models.Address;
import core.requests.locations.CreateAddressRequest;
import core.requests.locations.UpdateAddressRequest;
import core.service.IJwtService;
import core.service.IServiceFactory;
import core.servlets.mappers.IMapper;
import core.servlets.validators.IObjectValidator;
import core.servlets.validators.IValidatorFactory;
import services.JwtService;
import services.utils.factories.ServiceFactory;
import servlets.utils.mapper.objects.ObjectMapper;
import servlets.utils.validators.ValidatorFactory;

public abstract class AbstractServletBase {
	protected IServiceFactory serviceFactory;
	protected IValidatorFactory validatorFactory;
	protected IJwtService jwtService;
	protected IMapper mapper;

	public AbstractServletBase()
	{
		serviceFactory = new ServiceFactory();
		validatorFactory = new ValidatorFactory();
		jwtService = new JwtService();
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
