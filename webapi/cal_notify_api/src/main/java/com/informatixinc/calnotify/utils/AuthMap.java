package com.informatixinc.calnotify.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.informatixinc.calnotify.model.AccountType;
import com.informatixinc.calnotify.model.Login;

/**
 * Maps user logins
 * 
 * @author Sean Kammerich
 *
 */
public class AuthMap {

	private static ConcurrentHashMap<String, Login> logins = new ConcurrentHashMap<String, Login>();

	/**
	 * Add a login to the map
	 * 
	 * @param email
	 *            - user email
	 * @param accountType
	 *            - type of user account
	 * @return - a uuid
	 */
	public static String addLogin(String email, int accountType) {
		String uuid = UUID.randomUUID().toString();
		Login login = new Login();
		login.setAccountType(accountType);
		login.setUserName(email);
		login.setLogin(new Date());
		logins.put(uuid, login);

		return uuid;
	}

	/**
	 * Lookup session by email
	 * 
	 * @param email
	 *            - the email
	 * @return - session id
	 */
	public static String getSessionFromEmail(String email) {
		for (Map.Entry<String, Login> entry : logins.entrySet()) {
			if (entry.getValue().getUserName().equals(email)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Is the session type admin
	 * 
	 * @param sessionId
	 *            - session id
	 * @return - yes if admin
	 */
	public static boolean isAdmin(String sessionId) {
		if (logins.get(sessionId).getAccountType() == AccountType.ADMIN.getAccountType()) {
			return true;
		}

		return false;
	}

	/**
	 * Find the user name by session id
	 * 
	 * @param sessionId
	 *            - a session id
	 * @return - the user name
	 */
	public static String getUserName(String sessionId) {
		return logins.get(sessionId).getUserName();
	}

	/**
	 * Is the user authenticated
	 * 
	 * @param sessionId
	 *            - a session id
	 * @return - yes if authenticated
	 */
	public static boolean isAuthenticated(String sessionId) {
		cleanup();

		if (sessionId == null) {
			return false;
		}

		if (logins.containsKey(sessionId)) {
			logins.get(sessionId).setLogin(new Date());
			return true;
		} else {
			return false;
		}
	}

	private static void cleanup() {
		for (Map.Entry<String, Login> entry : logins.entrySet()) {
			if (entry.getValue().getLogin().getTime() < new Date().getTime() - 1000 * 60 * 30) {
				logins.remove(entry.getKey());
			}
		}
	}

}
