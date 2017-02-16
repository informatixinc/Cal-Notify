package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.Notification;

@Path("getactivenotifications")
public class GetActiveNotifications {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Notification> getNotifications(){
		NotificationDao notificationDao = new NotificationDao();
		
		return notificationDao.getActiveNotifications();
	}

}
