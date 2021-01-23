package servlets;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import core.domain.models.Address;
import core.requests.locations.CreateAddressRequest;
import core.requests.locations.UpdateAddressRequest;
import core.service.IJwtService;
import core.service.IServiceFactory;
import core.servlets.exceptions.UnauthorizedException;
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
	
	protected UUID isAuthorized(HttpServletRequest request)
	{
		String authorization = (String) request.getHeader("Authorization");
		if(authorization == null || !authorization.startsWith("Bearer "))
		{
			throw new UnauthorizedException();
		}
		
		String token = authorization.substring(7);
		UUID authenticatedUserId = jwtService.getUserIdDromJwtToken(token);
		if(authenticatedUserId == null)
		{
			throw new UnauthorizedException();
		}
		
		return authenticatedUserId;
	}
}
