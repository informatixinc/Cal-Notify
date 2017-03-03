package com.informatixinc.calnotify.model;

public class Session {
	
	private String session;
	private ErrorResponse errorResponse = new ErrorResponse();
	private int accountType;

	// get account type of user
	public int getAccountType() {
		return accountType;
	}
	
	// set account type of user
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}
	
	// get session of logged in user
	public String getSession() {
		return session;
	}
	
	// set session of logged in user
	public void setSession(String session) {
		this.session = session;
	}
	
	// get error response
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	
	// set error response
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
