package com.informatixinc.calnotify.model;

import java.util.Date;

public class Event {
	
	private String type;
	private String eventId;
	private String infoLink;
	private Date expires;
	private String gpsCoordinates;
	private String description;

	public Event(String type, String eventId, String infoLink, Date expires, 
			String gpsCoordinates, String event) {
		this.type = type;
		this.eventId = eventId;
		this.infoLink = infoLink;
		this.expires = expires;
		this.gpsCoordinates = gpsCoordinates;
		this.description = event;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(String infoLink) {
		this.infoLink = infoLink;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getGpsCoordinates() {
		return gpsCoordinates;
	}

	public void setGpsCoordinates(String gpsCoordinates) {
		this.gpsCoordinates = gpsCoordinates;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
