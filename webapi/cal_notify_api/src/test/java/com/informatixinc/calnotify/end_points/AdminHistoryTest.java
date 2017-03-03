package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import org.junit.Test;

import com.informatixinc.calnotify.model.AdminMessage;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.utils.AuthMap;
import static org.junit.Assert.*;
public class AdminHistoryTest {
	
	
	@Test
	public void testAdminHistory(){
		Session session = new Session();
		session.setSession(AuthMap.getSessionFromEmail("testsuite@informatixinc.com"));
		AdminHistoryEndpoint adminHistoryEndpoint = new AdminHistoryEndpoint();
		ArrayList<AdminMessage> messages = adminHistoryEndpoint.getMessages(session);
		assertTrue (messages.size() > 0);
	}

}
