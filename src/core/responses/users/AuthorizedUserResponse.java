package core.responses.users;

public class AuthorizedUserResponse {
	private WholeUserObjectResponseBase user;
	private String token;

	public AuthorizedUserResponse() {
		super();
	}

	public AuthorizedUserResponse(String token, WholeUserObjectResponseBase user) {
		super();
		this.token = token;
		this.user = user;
	}

	public WholeUserObjectResponseBase getUser() {
		return user;
	}

	public void setUser(WholeUserObjectResponseBase user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
