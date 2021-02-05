package core.service;

import java.time.LocalDate;
import java.util.UUID;

import core.domain.dto.BuyerTicketsForCalendar;

public interface ICalendarService {
	BuyerTicketsForCalendar getBuyerTicketsForDate(UUID buyerId, LocalDate wantedDate);
}
