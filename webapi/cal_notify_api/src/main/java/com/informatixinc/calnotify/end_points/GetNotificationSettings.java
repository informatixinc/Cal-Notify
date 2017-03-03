package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.utils.AuthMap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "getnotificationsettings" })
@Path("/getnotificationsettings")
public class GetNotificationSettings {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Fetch user user notifiation preferences")
	public ArrayList<NotificationSettings> getNotificationSettings(
			@ApiParam(value = "Object holding user session data", required = true) Session session) {
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.getNotificationSettings(AuthMap.getUserName(session.getSession()));
	}

}
