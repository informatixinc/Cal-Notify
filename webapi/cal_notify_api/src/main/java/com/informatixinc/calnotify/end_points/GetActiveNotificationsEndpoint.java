package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.Point;

import io.swagger.annotations.Api;

@Api(tags = {"getactivenotifications"})
@Path("getactivenotifications")
public class GetActiveNotificationsEndpoint {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Notification> getNotifications(Point point){
		NotificationDao notificationDao = new NotificationDao();
		
		return notificationDao.getActiveNotifications(point);
	}

}
