package com.saints.working;

public class UserProfile {
	private int volunteerID; //userID
	private String userFirstName; //companyName
	private String userLastName;
	private String signInName;
	private String password;	
	private String address;
	private String phoneNumber;
	private String email;
	
	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public String getSignInName() {
		return signInName;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public void setSignInName(String signInName) {
		this.signInName = signInName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
