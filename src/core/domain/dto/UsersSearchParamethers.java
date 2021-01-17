package core.domain.dto;

public class UsersSearchParamethers {
	private String name;
	private String surname;
	private String username;

	private String role;

	private String sortBy;
	private String orderBy;

	public UsersSearchParamethers() {
		super();
	}

	public UsersSearchParamethers(String name, String surname, String username, String role, String sortBy,
			String orderBy) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.role = role;
		this.sortBy = sortBy;
		this.orderBy = orderBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
