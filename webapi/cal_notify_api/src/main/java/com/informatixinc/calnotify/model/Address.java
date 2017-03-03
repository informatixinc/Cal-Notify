package com.informatixinc.calnotify.model;

public class Address {
	
	private String addressOne = "";
	private String addressTwo = "";
	private String state;
	private String city = "";
	private String zipCode = "";
	private String nickName;
	
	// get address one 
	public String getAddressOne() {
		return addressOne;
	}
	
	// set address one 
	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}
	
	// get address two
	public String getAddressTwo() {
		return addressTwo;
	}
	
	// set address two 
	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}
	
	// get state
	public String getState() {
		return state;
	}
	
	// set state
	public void setState(String state) {
		this.state = state;
	}
	
	// get city
	public String getCity() {
		return city;
	}
	
	// set state
	public void setCity(String city) {
		this.city = city;
	}
	
	//get zipcode
	public String getZipCode() {
		return zipCode;
	}
	
	//set zipcode
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	//get nick name
	public String getNickName() {
		return nickName;
	}
	
	//set nick name
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}	

}
