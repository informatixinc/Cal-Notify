package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import org.junit.Test;

import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.Point;

import static org.junit.Assert.*;

public class GetActiveNotificationsTest {
	
	@Test
	public void getActiveNotificationsLocationNotSet(){
		Point point = new Point();
		point.setLatitude(0);
		point.setLongitude(0);
		
		GetActiveNotificationsEndpoint getActiveNotificationsEndpoint = new GetActiveNotificationsEndpoint();
		ArrayList<Notification> notifications = getActiveNotificationsEndpoint.getNotifications(point);
		assertTrue(notifications.size() > 0);
		
	}
	
	@Test
	public void getActiveNotificationsLocationSet(){
		Point point = new Point();
		point.setLatitude(38.5816);
		point.setLongitude(-121.4944);
		
		GetActiveNotificationsEndpoint getActiveNotificationsEndpoint = new GetActiveNotificationsEndpoint();
		ArrayList<Notification> notifications = getActiveNotificationsEndpoint.getNotifications(point);
		assertTrue(notifications.size() > 0);
		
	}

}
