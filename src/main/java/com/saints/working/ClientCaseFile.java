package com.saints.working;

public class ClientCaseFile {
	private int caseFileID;
	private int volunteerID; 
	private int clientID;
	private String requestDate;
	private String initialReqMessage;
	private String homeVisitMessage;
	private String addMessage;
	
	
	public ClientCaseFile() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getCaseFileID() {
		return caseFileID;
	}


	public int getVolunteerID() {
		return volunteerID;
	}


	public int getClientID() {
		return clientID;
	}


	public String getRequestDate() {
		return requestDate;
	}


	public String getInitialReqMessage() {
		return initialReqMessage;
	}


	public String getHomeVisitMessage() {
		return homeVisitMessage;
	}


	public String getAddMessage() {
		return addMessage;
	}


	public void setCaseFileID(int caseFileID) {
		this.caseFileID = caseFileID;
	}


	public void setVolunteerID(int volunteerID) {
		this.volunteerID = volunteerID;
	}


	public void setClientID(int clientID) {
		this.clientID = clientID;
	}


	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}


	public void setInitialReqMessage(String initialReqMessage) {
		this.initialReqMessage = initialReqMessage;
	}


	public void setHomeVisitMessage(String homeVisitMessage) {
		this.homeVisitMessage = homeVisitMessage;
	}


	public void setAddMessage(String addMessage) {
		this.addMessage = addMessage;
	}
	
	
	
}
 
