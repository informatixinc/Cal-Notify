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

	// get id
	public int getId() {
		return id;
	}

	//set id
	public void setId(int id) {
		this.id = id;
	}

	// get title
	public String getTitle() {
		return title;
	}

	// set title
	public void setTitle(String title) {
		this.title = title;
	}

	// get image url
	public String getImageUrl() {
		return imageUrl;
	}

	// set image url
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	// get send time of message
	public Date getSendTime() {
		return sendTime;
	}

	// set send time of message
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	// get expiration time of message
	public Date getExpireTime() {
		return expireTime;
	}

	// set expiration time of message
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	// get notification id
	public String getNotificationId() {
		return notificationId;
	}

	// set notification id
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	// get type
	public String getType() {
		return type;
	}

	// set type
	public void setType(String type) {
		this.type = type;
	}

	// get classification id
	public int getClassificationId() {
		return classificationId;
	}

	// set classification id
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}

	// get info url
	public String getInfoUrl() {
		return infoUrl;
	}

	// set info url
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	// get type id
	public int getTypeId() {
		return typeId;
	}

	// set type id
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	// get point location of the address
	public Point getLocation() {
		return location;
	}

	// set point location of the address
	public void setLocation(Point location) {
		this.location = location;
	}

	// get Error response message
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	// set Error response message
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	// get admin message body
	public String getAdminMessageBody() {
		return adminMessageBody;
	}

	// set admin message body
	public void setAdminMessageBody(String adminMessageBody) {
		this.adminMessageBody = adminMessageBody;
	}
}
