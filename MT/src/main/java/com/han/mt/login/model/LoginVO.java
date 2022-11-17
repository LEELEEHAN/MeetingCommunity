package com.han.mt.login.model;

public class LoginVO {

	private String email;
	private String password;
	private String admin;
	
	
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginVO [email=" + email + ", password=" + password + ", admin="+ admin+"]";
	}
	
	
}
