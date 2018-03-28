package bll.model;

public class UserModel {

	private int user_id;
	private String username;
	private String password;
	private byte is_admin;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public byte getIs_admin() {
		return is_admin;
	}
	public void setIs_admin(byte is_admin) {
		this.is_admin = is_admin;
	}
	
}
