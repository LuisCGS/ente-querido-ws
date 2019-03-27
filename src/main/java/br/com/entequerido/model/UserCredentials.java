package br.com.entequerido.model;

public class UserCredentials {
	private String username;
	private String password;
	
	public UserCredentials() {
		super();
	}
	 
	public UserCredentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void getUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void getPassword(String password) {
		this.password = password;
	}
}
