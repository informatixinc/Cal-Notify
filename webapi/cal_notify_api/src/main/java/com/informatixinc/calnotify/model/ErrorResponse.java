package com.informatixinc.calnotify.model;

public class ErrorResponse {
	
	private boolean error;
	private String errorMessage;
	
	// return isError
	public boolean isError() {
		return error;
	}
	
	// set Error value
	public void setError(boolean success) {
		this.error = success;
	}
	
	// get ErrorMessage value
	public String getErrorMessage() {
		return errorMessage;
	}
	
	//set ErrorMessage value
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
