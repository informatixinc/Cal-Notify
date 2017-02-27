package com.informatixinc.calnotify.model;

public class SnsToken {
	private int userId;
	private String token;
	private SnsType type = SnsType.FCM;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public SnsType getType() {
		return type;
	}

	public void setType(SnsType type) {
		this.type = type;
	}
}
