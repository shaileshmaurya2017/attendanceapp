package com.attendance.model;


public class User {
	
	private String userName;
	private String password;
	private String emailId;
	private String phoneNO;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNO() {
		return phoneNO;
	}
	public void setPhoneNO(String phoneNO) {
		this.phoneNO = phoneNO;
	}
	public User(String userName, String password, String emailId, String phoneNO) {
		super();
		this.userName = userName;
		this.password = password;
		this.emailId = emailId;
		this.phoneNO = phoneNO;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", emailId=" + emailId + ", phoneNO=" + phoneNO
				+ "]";
	}
	
	

}
