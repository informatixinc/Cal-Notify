package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.AdminDao;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.utils.AuthMap;

import io.swagger.annotations.Api;

@Api(tags = {"adminmessagereport"})
@Path("/adminmessagereport")
public class AdminMessageReportEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Notification> getNotifications(Session session){
		
		if(!AuthMap.isAdmin(session.getSession())){
			PutResponse putResponse = new PutResponse();
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Not authorized");
			return new ArrayList<Notification>();
		}
		
		
		AdminDao adminDao = new AdminDao();
		return adminDao.getNotificationsForReport();
	}

}
