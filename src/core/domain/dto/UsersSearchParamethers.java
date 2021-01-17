package core.domain.dto;

public class UsersSearchParamethers {
	private String name;
	private String surname;
	private String username;

	public UsersSearchParamethers() {
		super();
	}

	public UsersSearchParamethers(String name, String surname, String username) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
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

}
