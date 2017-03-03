package com.informatixinc.calnotify.model;

public class AdminMessage {
	
	private String title;
	private String message;
	private long expirationDate;
	private String sentBy;
	
	//get title of admin message
	public String getTitle() {
		return title;
	}
	
	//set title of admin message
	public void setTitle(String title) {
		this.title = title;
	}
	
	//get message
	public String getMessage() {
		return message;
	}
	
	//set message
	public void setMessage(String message) {
		this.message = message;
	}
	
	// get expiration date of admin message
	public long getExpirationDate() {
		return expirationDate;
	}
	
	//set expiration date of admin message
	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	// get sent by information
	public String getSentBy() {
		return sentBy;
	}
	
	// set sent by information
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}
}
