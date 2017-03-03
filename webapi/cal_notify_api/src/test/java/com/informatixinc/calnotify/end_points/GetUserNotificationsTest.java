package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import org.junit.Test;

import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.utils.AuthMap;

import static org.junit.Assert.*;

public class GetUserNotificationsTest {
	
	@Test
	public void getNotificationSettings(){
		Session session = new Session();
		session.setSession(AuthMap.getSessionFromEmail("testsuite@informatixinc.com"));
		
		GetNotificationSettings getNotificationSettings = new GetNotificationSettings();
		ArrayList<NotificationSettings> notificationSettings = getNotificationSettings.getNotificationSettings(session);
		assertTrue (notificationSettings.size() > 0);
	}
}
