package core.requests.users;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.Gender;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class CreateBuyerRequest {
	private String Username;
	private String Password;
	private String Name;
	private String Surname;
	private Gender gender;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime birthdate;
	private UUID buyerTypeId;
	
	public CreateBuyerRequest() {
		super();
	}

	public CreateBuyerRequest(String username, String password, String name, String surname, Gender gender,
			LocalDateTime birthdate, UUID buyerTypeId) {
		super();
		Username = username;
		Password = password;
		Name = name;
		Surname = surname;
		this.gender = gender;
		this.birthdate = birthdate;
		this.buyerTypeId = buyerTypeId;
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
	
	public UUID getBuyerTypeId() {
		return buyerTypeId;
	}
	
	public void setBuyerTypeId(UUID buyerTypeId) {
		this.buyerTypeId = buyerTypeId;
	}
}
