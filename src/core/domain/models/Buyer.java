package core.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public class Buyer extends User {
	@JsonIgnore
	private List<Ticket> tickets;
	private int points;
	private UUID typeId;
	@JsonIgnore
	private BuyerType type;
	
	public Buyer()
	{
		super();
		this.tickets = new ArrayList<Ticket>(); 
		this.setRole(UserRole.Buyer);
	}
	
	public Buyer(String username, String password, String name, String surname,
			Gender gender, LocalDateTime birthdate, List<Ticket> tickets,
			int points, UUID typeId, BuyerType type) {
		super(username, password, name, surname, gender, birthdate, UserRole.Buyer);
		this.tickets = tickets;
		this.points = points;
		this.typeId = typeId;
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

	public UUID getTypeId() {
		return typeId;
	}

	public void setTypeId(UUID typeId) {
		this.typeId = typeId;
	}

	public BuyerType getType() {
		return type;
	}

	public void setType(BuyerType type) {
		this.type = type;
	}
}
