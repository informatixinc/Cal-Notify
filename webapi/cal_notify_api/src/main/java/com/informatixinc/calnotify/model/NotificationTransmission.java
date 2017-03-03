package com.informatixinc.calnotify.model;

import java.util.Date;

public class NotificationTransmission {
	private int userId;
	private int notificationId;
	private int transmissionTypeId;
	private Date sendTime;

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

	// get transmission type id
	public int getTransmissionTypeId() {
		return transmissionTypeId;
	}

	// set transmission type id
	public void setTransmissionTypeId(int transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}

	// get notification send time
	public Date getSendTime() {
		return sendTime;
	}

	// set notification set time
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
