package com.informatixinc.calnotify.model;

public class UserSettings {
	private int userId;
	private String email;
	private String phoneNumber;
	private boolean sendSms;
	private boolean sendEmail;
	private boolean sendSns;
	private String token;

	// get user id
	public int getUserId() {
		return userId;
	}

	// set user id
	public void setUserId(int userId) {
		this.userId = userId;
	}

	// get email
	public String getEmail() {
		return email;
	}

	// set email
	public void setEmail(String email) {
		this.email = email;
	}

	// get phone number
	public String getPhoneNumber() {
		return phoneNumber;
	}

	// set phone number
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	// return send sms value
	public boolean isSendSms() {
		return sendSms;
	}

	// set send sms value
	public void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}

	// return send email value
	public boolean isSendEmail() {
		return sendEmail;
	}

	// set send email value
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	// return send sns value
	public boolean isSendSns() {
		return sendSns;
	}

	// set send sns vlaue
	public void setSendSns(boolean sendSns) {
		this.sendSns = sendSns;
	}

	// get token value
	public String getToken() {
		return token;
	}

	// set token value
	public void setToken(String token) {
		this.token = token;
	}
}
