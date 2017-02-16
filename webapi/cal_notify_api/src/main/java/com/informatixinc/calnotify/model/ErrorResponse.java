package com.informatixinc.calnotify.model;

public class ErrorResponse {
	
	private boolean error;
	private String errorMessage;
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean success) {
		this.error = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
