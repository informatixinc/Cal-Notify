package com.informatixinc.calnotify.model;

public class UserEvent {
	
	public UserEvent(String event, int count){
		this.count = count;
		this.event = event;
	}
	
	private String event;
	private int count;
	
	// get event
	public String getEvent() {
		return event;
	}
	
	// set event
	public void setEvent(String event) {
		this.event = event;
	}
	
	// get count 
	public int getCount() {
		return count;
	}
	
	// set count
	public void setCount(int count) {
		this.count = count;
	}

	
	
	

}
