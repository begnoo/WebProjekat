package core.requests.users;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.Gender;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class UpdateUserRequest {
	private UUID id;
	private String name;
	private String surname;
	private Gender gender;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime birthdate;
	
	public UpdateUserRequest() {
		super();
	}

	public UpdateUserRequest(UUID id, String name, String surname, Gender gender,
			LocalDateTime birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthdate = birthdate;
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
}