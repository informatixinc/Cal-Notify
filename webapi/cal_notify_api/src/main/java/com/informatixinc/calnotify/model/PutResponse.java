package com.informatixinc.calnotify.model;

public class PutResponse {
	
	private ErrorResponse errorResponse = new ErrorResponse();

	// get error response message
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	// set error response message
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

}
