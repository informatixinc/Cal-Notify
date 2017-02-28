package com.informatixinc.calnotify.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.Notification;

import io.swagger.annotations.Api;

@Api(tags = {"getnotification"})
@Path("getnotification")
public class GetNotificationByIdEndpoint {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Notification getNotifications(Notification notification){
		NotificationDao notificationDao = new NotificationDao();
		
		return notificationDao.getNotificationById(notification.getId());
	}

}
