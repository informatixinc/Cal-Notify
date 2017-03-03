package com.informatixinc.calnotify.model;

public class SnsToken {
	private int userId;
	private String token;
	private SnsType type = SnsType.FCM;

	// get user id
	public int getUserId() {
		return userId;
	}

	// set user id
	public void setUserId(int userId) {
		this.userId = userId;
	}

	// get sns token
	public String getToken() {
		return token;
	}

	// set sns token
	public void setToken(String token) {
		this.token = token;
	}

	// get sns type
	public SnsType getType() {
		return type;
	}

	// set sns type
	public void setType(SnsType type) {
		this.type = type;
	}
}
