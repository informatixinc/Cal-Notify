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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "getactivenotifications" })
@Path("getactivenotifications")
public class GetActiveNotificationsEndpoint {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Fetch notifications that have not expired")
	public ArrayList<Notification> getNotifications(
			@ApiParam(value = "Object holding geographic coordinates", required = true) Point point) {
		NotificationDao notificationDao = new NotificationDao();

		return notificationDao.getActiveNotifications(point);
	}

}
