package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.NotificationsWrapper;
import com.informatixinc.calnotify.model.PutResponse;

import io.swagger.annotations.Api;

@Api(tags = {"managenotifications"})
@Path("/managenotifications")
public class ManageNotificationsEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse manageNotificationSettings(NotificationsWrapper nw){
		ArrayList<NotificationSettings> settings = nw.getNotificationSettings();
		PutResponse putResponse = new PutResponse();
		
		if(settings.size() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("No settings passed");
			return putResponse;
		}
		
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.updateNotificationSettings(settings, putResponse);
	}

}
