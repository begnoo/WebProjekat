package services.utils.factories.creators;

import core.service.ICalendarService;
import core.service.IServiceCreator;
import core.service.ITicketService;
import repository.DbContext;
import services.CalendarService;

public class CalendarServiceCreator implements IServiceCreator<ICalendarService> {

	@Override
	public ICalendarService create(DbContext context) {
		ITicketService ticketService = new TicketServiceCreator().create(context);
		
		return new CalendarService(ticketService);
	}

}
