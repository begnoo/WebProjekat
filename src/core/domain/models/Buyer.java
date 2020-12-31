package core.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class Buyer extends User {
	private List<Ticket> tickets;
	private int points;
	private UUID buyerTypeId;
	private BuyerType type;
	
	public Buyer()
	{
		super();
		this.tickets = new ArrayList<Ticket>(); 
	}
	
	public Buyer(String username, String password, String name, String surname,
			Gender gender, LocalDateTime birthdate, List<Ticket> tickets,
			int points, UUID buyerTypeId, BuyerType type) {
		super(username, password, name, surname, gender, birthdate, UserRole.Buyer);
		this.tickets = tickets;
		this.points = points;
		this.buyerTypeId = buyerTypeId;
		this.type = type;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public UUID getBuyerTypeId() {
		return buyerTypeId;
	}

	public void setBuyerTypeId(UUID buyerTypeId) {
		this.buyerTypeId = buyerTypeId;
	}

	public BuyerType getType() {
		return type;
	}

	public void setType(BuyerType type) {
		this.type = type;
	}
}
