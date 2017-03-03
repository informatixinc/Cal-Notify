package com.informatixinc.calnotify.end_points;

import org.junit.Test;

import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.AuthMap;

import static org.junit.Assert.*;

public class AccountInformationTest {
	
	@Test
	public void accountInformation(){
		Session session = new Session();
		session.setSession(AuthMap.getSessionFromEmail("resident@norcal.informatixinc.com"));
		AccountInformationEndpoint accountInformationEndpoint = new AccountInformationEndpoint();
		User response = accountInformationEndpoint.accountInformation(session);
		assertTrue(response.getEmail().equals("resident@norcal.informatixinc.com"));
	}

}
