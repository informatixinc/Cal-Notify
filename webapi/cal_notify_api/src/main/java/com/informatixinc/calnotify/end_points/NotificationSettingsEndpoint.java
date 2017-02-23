package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.utils.AuthMap;

@Path("/notificationsettings")
public class NotificationSettingsEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse setNotificationSettings(NotificationSettings settings){
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.addNotificationSettings(settings);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<NotificationSettings> getNotificationSettings(@PathParam("email") String sessionId){
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.getNotificationSettings(AuthMap.getUserName(sessionId));
	}

}
