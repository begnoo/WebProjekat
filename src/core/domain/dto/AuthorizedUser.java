package core.domain.dto;

import core.domain.models.User;

public class AuthorizedUser {
	private User user;
	private String token;
	
	public AuthorizedUser() {
		super();
	}
	public AuthorizedUser(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
