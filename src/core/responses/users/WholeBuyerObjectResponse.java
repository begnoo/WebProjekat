package core.responses.users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;
import core.domain.models.BuyerType;
import core.domain.models.Ticket;

public class WholeBuyerObjectResponse extends WholeUserObjectResponseBase {
	private List<Ticket> tickets;
	private int points;
	private UUID typeId;
	private BuyerType type;

	public WholeBuyerObjectResponse() {
		super();
	}

	public WholeBuyerObjectResponse(UUID id, LocalDateTime createdAt, boolean active, String username, String password,
			String name, String surname, Gender gender, LocalDateTime birthdate, UserRole role, List<Ticket> tickets,
			int points, UUID typeId, BuyerType type) {
		super(id, createdAt, active, username, password, name, surname, gender, birthdate, role);

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
