package services.utils.factories;

import java.util.HashMap;

import core.service.IAuthorizationService;
import core.service.IBuyerTypeService;
import core.service.ICommentService;
import core.service.IImageService;
import core.service.IJwtService;
import core.service.ILocationService;
import core.service.IManifestationService;
import core.service.IServiceCreator;
import core.service.IServiceFactory;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import core.service.IUserService;
import repository.DbContext;
import services.utils.factories.creators.AuthorizationServiceCreator;
import services.utils.factories.creators.BuyerTypeServiceCreator;
import services.utils.factories.creators.CommentServiceCreator;
import services.utils.factories.creators.ImageServiceCreator;
import services.utils.factories.creators.JwtServiceCreator;
import services.utils.factories.creators.LocationServiceCreator;
import services.utils.factories.creators.ManifestationServiceCreator;
import services.utils.factories.creators.TicketOrderServiceCreator;
import services.utils.factories.creators.TicketServiceCreator;
import services.utils.factories.creators.UserServiceCreator;

public class ServiceFactory implements IServiceFactory {
	private HashMap<Class<?>, Class<?>> creators;

	public ServiceFactory()
	{
		creators = new HashMap<Class<?>, Class<?>>();
		creators.put(IJwtService.class, JwtServiceCreator.class);
		creators.put(IAuthorizationService.class, AuthorizationServiceCreator.class);
		creators.put(IBuyerTypeService.class, BuyerTypeServiceCreator.class);
		creators.put(ICommentService.class, CommentServiceCreator.class);
		creators.put(IImageService.class, ImageServiceCreator.class);
		creators.put(ILocationService.class, LocationServiceCreator.class);
		creators.put(IManifestationService.class, ManifestationServiceCreator.class);
		creators.put(ITicketService.class, TicketServiceCreator.class);
		creators.put(ITicketOrderService.class, TicketOrderServiceCreator.class);
		creators.put(IUserService.class, UserServiceCreator.class);
	}
	
	@Override
	public Object getService(Class<?> serviceClass, DbContext context) {
		Class<?> classOfCreator = creators.get(serviceClass);
		
		IServiceCreator<?> creator = null;
		try {
			creator = (IServiceCreator<?>) classOfCreator.getConstructor()
												  		 .newInstance();
			
			return creator.create(context);
		} catch (Exception e) {
			System.out.println("Error while trying to instantiate service for: " + serviceClass.getSimpleName());
		} 
		
		return null;
	}


}
