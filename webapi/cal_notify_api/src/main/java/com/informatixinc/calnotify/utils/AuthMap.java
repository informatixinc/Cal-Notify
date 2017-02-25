package com.informatixinc.calnotify.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.informatixinc.calnotify.model.AccountType;
import com.informatixinc.calnotify.model.Login;


public class AuthMap {
	
	private static ConcurrentHashMap<String, Login> logins = new ConcurrentHashMap<String, Login>();
	
	public static String addLogin(String email, int accountType){
		String uuid =  UUID.randomUUID().toString();
		Login login = new Login();
		login.setAccountType(accountType);
		login.setUserName(email);
		login.setLogin(new Date());
		logins.put(uuid, login);
		
		return uuid;
	}
	
	public static boolean isAdmin(String sessionId){
		if(logins.get(sessionId).getAccountType() == AccountType.ADMIN.getAccountType()){
			return true;
		}
		
		return false;
	}
	
	public static String getUserName(String sessionId){
		return logins.get(sessionId).getUserName();
	}
	
	public static boolean isAuthenticated(String sessionId){
		cleanup();
		
		if(sessionId == null){
			return false;
		}
		
		if(logins.containsKey(sessionId)){
			logins.get(sessionId).setLogin(new Date());
			return true;
		}else{
			return false;
		}
	}
	
	private static void cleanup(){
		for(Map.Entry<String, Login> entry : logins.entrySet() ){
			if(entry.getValue().getLogin().getTime() < new Date().getTime() - 1000 * 60 *30){
				logins.remove(entry.getKey());
			}
		}
	}

}
