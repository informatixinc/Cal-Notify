package com.informatixinc.calnotify.model;

import java.util.Date;

public class Notification {
	
	private String title;
	private String imageUrl;
	private Date issuetime;
	private Date expireTime;
	private int notificationId;
	private ErrorResponse errorResponse = new ErrorResponse();
	
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
	public Date getIssuetime() {
		return issuetime;
	}
	public void setIssuetime(Date issuetime) {
		this.issuetime = issuetime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}
	
}
