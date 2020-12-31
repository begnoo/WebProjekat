package core.domain.models;

import java.time.LocalDateTime;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;

public abstract class User extends BaseEntity {
	private String Username;
	private String Password;
	private String Name;
	private String Surname;
	private Gender gender;
	private LocalDateTime birthdate;
	private UserRole role;
	
	public User()
	{
		super();	
	}
	
	public User(String username, String password, String name, String surname,
			Gender gender, LocalDateTime birthdate, UserRole role) {
		super();
		Username = username;
		Password = password;
		Name = name;
		Surname = surname;
		this.gender = gender;
		this.birthdate = birthdate;
		this.role = role;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDateTime getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDateTime birthdate) {
		this.birthdate = birthdate;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
