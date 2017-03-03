package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import org.junit.Test;

import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.utils.AuthMap;
import static org.junit.Assert.*;
public class AdminMessageReportTest {
	
	@Test
	public void adminMessageReport(){
		Session session = new Session();
		session.setSession(AuthMap.getSessionFromEmail("testsuite@informatixinc.com"));
		
		AdminMessageReportEndpoint adminMessageReportEndpoint = new AdminMessageReportEndpoint();
		ArrayList<Notification> notifications = adminMessageReportEndpoint.getNotifications(session);
		assertTrue (notifications.size() > 0);
	}

}
