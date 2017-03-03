package com.informatixinc.calnotify.model;

import java.util.Date;

public class UserNotification {

	private int notificationId;
	private String notificationType;
	private String notificationTitle;
	private String infoUrl;
	private Date expires;
	private Date sendTime;
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private boolean sendSms = false;
	private boolean sendEmail = false;
	private boolean sendPush = false;
	private String snsToken = null;

	// get user id
	public int getUserId() {
		return userId;
	}

	// set user id
	public void setUserId(int userId) {
		this.userId = userId;
	}

	// get notification id
	public int getNotificationId() {
		return notificationId;
	}

	// set notification id
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	// get notification type
	public String getNotificationType() {
		return notificationType;
	}

	// set notification type
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	// get notification title
	public String getNotificationTitle() {
		return notificationTitle;
	}

	// set notification title
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	// get info url
	public String getInfoUrl() {
		return infoUrl;
	}

	// set info url
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	// get expiration date of notification
	public Date getExpires() {
		return expires;
	}

	// set expiration date of notification
	public void setExpires(Date expires) {
		this.expires = expires;
	}

	// get send time of notification
	public Date getSendTime() {
		return sendTime;
	}

	// set send time of notification 
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	// get first name
	public String getFirstName() {
		return firstName;
	}

	// set first name
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	// get last name
	public String getLastName() {
		return lastName;
	}

	// set last name
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	// return send push value
	public boolean isSendPush() {
		return sendPush;
	}

	// set send push value
	public void setSendPush(boolean sendPush) {
		this.sendPush = sendPush;
	}

	// get sns token
	public String getSnsToken() {
		return snsToken;
	}

	// set sns token
	public void setSnsToken(String snsToken) {
		this.snsToken = snsToken;
	}

}
