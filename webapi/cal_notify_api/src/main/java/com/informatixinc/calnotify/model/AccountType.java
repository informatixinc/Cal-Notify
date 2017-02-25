package com.informatixinc.calnotify.model;

public enum AccountType {
	
	ADMIN(1),
	USER(2);
	
	private int accountType;
	
	AccountType(int accountType){
		this.accountType = accountType;
	}
	
	public int getAccountType(){
		return this.accountType;
	}

}
