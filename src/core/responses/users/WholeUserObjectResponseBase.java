package core.responses.users;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class WholeUserObjectResponseBase {
	private UUID id;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime createdAt;
	private boolean active;
	private String Username;
	private String Password;
	private String Name;
	private String Surname;
	private Gender gender;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime birthdate;
	private UserRole role;

	public WholeUserObjectResponseBase() {
		super();
	}

	public WholeUserObjectResponseBase(UUID id, LocalDateTime createdAt, boolean active, String username,
			String password, String name, String surname, Gender gender, LocalDateTime birthdate, UserRole role) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.active = active;
		Username = username;
		Password = password;
		Name = name;
		Surname = surname;
		this.gender = gender;
		this.birthdate = birthdate;
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
