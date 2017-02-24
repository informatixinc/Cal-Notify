package com.informatixinc.calnotify.model;

public class NotificationRequest {
	
	boolean localized;
	double latitude;
	double longitude;
	
	
	public boolean isLocalized() {
		return localized;
	}
	public void setLocalized(boolean localized) {
		this.localized = localized;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	

}
