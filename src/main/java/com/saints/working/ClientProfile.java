package com.saints.working;

public class ClientProfile {
	private int clientID; 
	private int volunteerID;
	private String clientFirstName; 
	private String clientMidName;
	private String clientLastName;
	private String birthdate;
	private String address;
	private String phoneNumber;
	private String email;
	
	
	
	public ClientProfile() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getClientMidName() {
		return clientMidName;
	}



	public void setClientMidName(String clientMidName) {
		this.clientMidName = clientMidName;
	}



	public int getVolunteerID() {
		return volunteerID;
	}



	public void setVolunteerID(int volunteerID) {
		this.volunteerID = volunteerID;
	}



	public int getClientID() {
		return clientID;
	}



	public String getClientFirstName() {
		return clientFirstName;
	}



	public String getClientLastName() {
		return clientLastName;
	}



	public String getBirthdate() {
		return birthdate;
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



	public void setClientID(int clientID) {
		this.clientID = clientID;
	}



	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}



	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}



	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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
