package repository.utils.deserializators;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.domain.enums.Gender;
import core.domain.enums.UserRole;
import core.domain.models.Administrator;
import core.domain.models.Buyer;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IEntitiesDeserializator;

public class UsersDeserializator implements IEntitiesDeserializator<User>{
	private ObjectMapper mapper;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public UsersDeserializator()
	{
		this.mapper = new ObjectMapper();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> Deserialize(String fileName) throws JsonParseException, JsonMappingException, IOException {
		List<User> users = new ArrayList<User>();
		List<LinkedHashMap> listOfMappedEntities = mapper.readValue(new File(fileName), new ArrayList<LinkedHashMap>().getClass());
		
		for(LinkedHashMap entityMap : listOfMappedEntities) {
			String role = (String) entityMap.get("role");
			
			try {
				User user = null;

				if(role.equals("Buyer")) {
					user = new Buyer();
				} else if (role.equals("Seller")) {
					user = new Seller();
				} else {
					user = new Administrator();
				}
				
				user.setId(UUID.fromString((String) entityMap.get("id")));
				user.setCreatedAt(LocalDateTime.parse((String) entityMap.get("createdAt"), formatter));
				user.setActive((boolean) entityMap.get("active"));
				user.setGender(Gender.valueOf((String) entityMap.get("gender")));
				user.setBirthdate(LocalDateTime.parse((String) entityMap.get("birthdate"), formatter));
				user.setName((String) entityMap.get("name"));
				user.setSurname((String) entityMap.get("surname"));
				user.setUsername((String) entityMap.get("username"));
				user.setPassword((String) entityMap.get("password"));
				
				if(role.equals("Buyer")) {
					Buyer buyer = (Buyer) user;
					buyer.setRole(UserRole.Buyer);
					buyer.setPoints((Integer) entityMap.get("points"));
					buyer.setBuyerTypeId(UUID.fromString((String) entityMap.get("buyerTypeId")));
					users.add(buyer);
				}
				else if (role.equals("Seller")) {
					Seller seller = (Seller) user;
					seller.setRole(UserRole.Seller);
					users.add(seller);
				}
				else {
					Administrator administrator = (Administrator) user;
					user.setRole(UserRole.Administrator);
					users.add(administrator);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Bad user role.");
			}
		}
		
		return users;
	}


}
