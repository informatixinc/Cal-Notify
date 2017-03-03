package com.informatixinc.calnotify.model;

import java.util.Date;

public class Login {
	
	private String userName;
	private int accountType;
	private Date login;
	
	// get user name value
	public String getUserName() {
		return userName;
	}
	
	// set user name
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	// get login date
	public Date getLogin() {
		return login;
	}
	
	// set login date value
	public void setLogin(Date login) {
		this.login = login;
	}
	
	//get account type of logged in person
	public int getAccountType() {
		return accountType;
	}
	
	// set account type value
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

}
