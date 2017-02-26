package com.informatixinc.calnotify.model;

public class UserEvent {
	
	public UserEvent(String event, int count){
		this.count = count;
		this.event = event;
	}
	
	private String event;
	private int count;
	
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
	
	

}
