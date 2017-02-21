package com.informatixinc.calnotify.model;

import java.util.Date;

public class NotificationTransmission {
	private int userId;
	private int notificationId;
	private int transmissionTypeId;
	private Date sendTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public int getTransmissionTypeId() {
		return transmissionTypeId;
	}

	public void setTransmissionTypeId(int transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
