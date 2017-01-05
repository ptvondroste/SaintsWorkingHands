package com.saints.working;



public class User_Client_Case {
	
	private ClientProfile clientprofile;
	private UserProfile userprofile;
	private ClientCaseFile clientcasefile;
	
	
	public User_Client_Case(ClientProfile clientprofile, UserProfile userprofile, ClientCaseFile clientcasefile) {
		super();
		this.clientprofile = clientprofile;
		this.userprofile = userprofile;
		this.clientcasefile = clientcasefile;
	}


	public ClientProfile getClientprofile() {
		return clientprofile;
	}


	public UserProfile getUserprofile() {
		return userprofile;
	}


	public ClientCaseFile getClientcasefile() {
		return clientcasefile;
	}


	public void setClientprofile(ClientProfile clientprofile) {
		this.clientprofile = clientprofile;
	}


	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}


	public void setClientcasefile(ClientCaseFile clientcasefile) {
		this.clientcasefile = clientcasefile;
	}
	
	
	
}
