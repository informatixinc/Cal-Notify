package com.informatixinc.calnotify.model;

public class NotificationSettings {
	
	private boolean sms;
	private boolean email;
	private boolean sns;
	private int userLocationId;
	
	public boolean isSms() {
		return sms;
	}
	public void setSms(boolean sms) {
		this.sms = sms;
	}
	public boolean isEmail() {
		return email;
	}
	public void setEmail(boolean email) {
		this.email = email;
	}
	public boolean isSns() {
		return sns;
	}
	public void setSns(boolean sns) {
		this.sns = sns;
	}
	public int getUserLocationId() {
		return userLocationId;
	}
	public void setUserLocationId(int userLocationId) {
		this.userLocationId = userLocationId;
	}

}
