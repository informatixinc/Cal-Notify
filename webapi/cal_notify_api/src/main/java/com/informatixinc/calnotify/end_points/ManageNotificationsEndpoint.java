package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.PutResponse;

@Path("/managenotifications")
public class ManageNotificationsEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse manageNotificationSettings(ArrayList<NotificationSettings> settings){
		PutResponse putResponse = new PutResponse();
		
		if(settings.size() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("No setting passed");
			return putResponse;
		}
		
		NotificationDao notificationDao = new NotificationDao();
		return notificationDao.updateNotificationSettings(settings, putResponse);
	}

}