package core.domain.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public abstract class User extends BaseEntity {
	private String Username;
	private String Password;
	private String Salt;
	private String Name;
	private String Surname;
	private Gender gender;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime birthdate;
	private UserRole role;

	public User() {
		super();
	}

	public User(String username, String password, String salt, String name, String surname, Gender gender,
			LocalDateTime birthdate, UserRole role) {
		super();
		Username = username;
		Password = password;
		Salt = salt;
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

	public String getSalt() {
		return Salt;
	}

	public void setSalt(String salt) {
		Salt = salt;
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
