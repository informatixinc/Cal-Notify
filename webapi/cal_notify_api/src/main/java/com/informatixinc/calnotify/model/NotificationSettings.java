package com.informatixinc.calnotify.model;

public class NotificationSettings {
	
	private boolean sms;
	private boolean email;
	private boolean sns;
	private int userLocationId;
	private int notificationId;
	private String nickName;
	
	// return sms value 
	public boolean isSms() {
		return sms;
	}
	
	// set sms value
	public void setSms(boolean sms) {
		this.sms = sms;
	}
	
	// return email value 
	public boolean isEmail() {
		return email;
	}
	
	// set email value
	public void setEmail(boolean email) {
		this.email = email;
	}
	
	// return sns value
	public boolean isSns() {
		return sns;
	}
	
	// set sns value
	public void setSns(boolean sns) {
		this.sns = sns;
	}
	
	// get user location id
	public int getUserLocationId() {
		return userLocationId;
	}
	
	// set user location id
	public void setUserLocationId(int userLocationId) {
		this.userLocationId = userLocationId;
	}
	
	// get notification id
	public int getNotificationId() {
		return notificationId;
	}
	
	// set notification id
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	
	// get nick name
	public String getNickName() {
		return nickName;
	}
	
	// set nick name
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
