package services.utils.factories.creators;

import core.domain.models.BuyerType;
import core.domain.models.Manifestation;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IServiceCreator;
import core.service.ITicketOrderService;
import core.service.ITicketService;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import repository.UserRepository;
import services.TicketOrderService;

public class TicketOrderServiceCreator implements IServiceCreator<ITicketOrderService> {

	@Override
	public ITicketOrderService create(DbContext context) {
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		
		ITicketService ticketService = new TicketServiceCreator().create(context);
		
		return new TicketOrderService(ticketService, userRepository, manifestationRepository, buyerTypeRepository);
	}

}
