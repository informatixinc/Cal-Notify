package com.informatixinc.calnotify.model;

public class PutResponse {
	
	private ErrorResponse errorResponse = new ErrorResponse();

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

}
