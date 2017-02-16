package com.informatixinc.calnotify.end_points;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.Notification;

@Path("getnotification")
public class GetNotificationById {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Notification getNotifications(@PathParam("id") int id){
		NotificationDao notificationDao = new NotificationDao();
		
		return notificationDao.getNotificationById(id);
	}

}
