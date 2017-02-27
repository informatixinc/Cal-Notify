package com.informatixinc.calnotify.model;

import java.util.Date;

public class Notification {
	private int id;
	private String type;
	private int typeId;
	private int classificationId;
	private String title;
	private String imageUrl;
	private String infoUrl;
	private String notificationId;
	private String adminMessageBody;
	private Date sendTime;
	private Date expireTime;
	private Point location;
	private ErrorResponse errorResponse = new ErrorResponse();

	public Notification() {

	}

	public Notification(String type, int typeId, int classificationId, String title, String infoUrl,
			String notificationId, Date sendTime, Date expireTime, Point location) {
		this.type = type;
		this.typeId = typeId;
		this.classificationId = classificationId;
		this.title = title;
		this.infoUrl = infoUrl;
		this.notificationId = notificationId;
		this.sendTime = sendTime;
		this.expireTime = expireTime;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	public String getAdminMessageBody() {
		return adminMessageBody;
	}

	public void setAdminMessageBody(String adminMessageBody) {
		this.adminMessageBody = adminMessageBody;
	}
}
