package services.utils.factories.creators;

import core.domain.models.Ticket;
import core.repository.IRepository;
import core.service.IServiceCreator;
import core.service.ITicketService;
import core.service.IUserTicketManifestationMediator;
import repository.DbContext;
import repository.TicketRepository;
import services.TicketService;
import services.UserTicketManifestationMediator;

public class TicketServiceCreator implements IServiceCreator<ITicketService> {

	@Override
	public ITicketService create(DbContext context) {
		IUserTicketManifestationMediator mediator = new UserTicketManifestationMediator(context);

		IRepository<Ticket> ticketRepository = new TicketRepository(context);

		return new TicketService(ticketRepository, mediator);
	}

}
