package com.informatixinc.calnotify.model;

public class NotificationRequest {
	
	boolean localized;
	double latitude;
	double longitude;
	
	// return value Localized
	public boolean isLocalized() {
		return localized;
	}
	
	// set value of localized
	public void setLocalized(boolean localized) {
		this.localized = localized;
	}
	
	// get latitude
	public double getLatitude() {
		return latitude;
	}
	
	// set latitude
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	// get longitude
	public double getLongitude() {
		return longitude;
	}
	
	// set longitude
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
