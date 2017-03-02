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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "setnotificationsettings" })
@Path("/setnotificationsettings")
public class SetNotificationSettingsEndpoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add user notification preferences")
	public PutResponse setNotificationSettings(
			@ApiParam(value = "Object holding user notification preferences", required = true) NotificationSettings settings) {
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.addNotificationSettings(settings);
	}
}
