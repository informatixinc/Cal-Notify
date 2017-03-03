package com.informatixinc.calnotify.model;

public class Point {
	
	private double latitude;
	private double longitude;
	private ErrorResponse errorResponse = new ErrorResponse();
	
	// get latitude of location
	public double getLatitude() {
		return latitude;
	}
	
	// set latitude of location
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	// get longitude of location
	public double getLongitude() {
		return longitude;
	}
	
	// set longitude of location
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	// concat latitude and longitude
	@Override
	public String toString() {
		return "Point [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
	// get error response message
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	
	// set error response message
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
}
