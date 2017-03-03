package com.informatixinc.calnotify.model;

import java.util.ArrayList;

public class User {
	
	private String email = "";
	private String password = "";
	private String firstName = "";
	private String lastName = "";
	private String phoneNumber = "";
	private String accountType;
	private ArrayList<Address> addresses;
	private Point location;
	
	// get user email
	public String getEmail() {
		return email;
	}
	
	// set user email
	public void setEmail(String email) {
		this.email = email;
	}
	
	// get user password
	public String getPassword() {
		return password;
	}
	
	// set user password
	public void setPassword(String password) {
		this.password = password;
	}
	
	// get firat name of user
	public String getFirstName() {
		return firstName;
	}
	
	// set firat name of user
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	// get last name of user
	public String getLastName() {
		return lastName;
	}
	
	// set last name of user
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	// get phone number of user
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	// set phone of user
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	// get account type of user
	public String getAccountType() {
		return accountType;
	}
	
	// set account type of user
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	// get point location of user
	public Point getLocation() {
		return location;
	}
	
	// set point location of user
	public void setLocation(Point location) {
		this.location = location;
	}
	
	// get address array of user
	public ArrayList<Address> getAddresses() {
		return addresses;
	}
	
	// set address array of user
	public void setAddresses(ArrayList<Address> addresses) {
		this.addresses = addresses;
	}
	
		
}
