package core.requests.users;

public class ChangePasswordRequest {
	private String newPassword;
	private String currentPassword;
	
	public ChangePasswordRequest() 
	{
	
	}
	
	public ChangePasswordRequest(String newPassword, String currentPassword) {
		super();
		this.newPassword = newPassword;
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
}