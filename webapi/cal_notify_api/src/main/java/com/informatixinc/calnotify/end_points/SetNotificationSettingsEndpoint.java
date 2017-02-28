package com.informatixinc.calnotify.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.PutResponse;

import io.swagger.annotations.Api;

@Api(tags = {"setnotificationsettings"})
@Path("/setnotificationsettings")
public class SetNotificationSettingsEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse setNotificationSettings(NotificationSettings settings){
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.addNotificationSettings(settings);
	}
}
