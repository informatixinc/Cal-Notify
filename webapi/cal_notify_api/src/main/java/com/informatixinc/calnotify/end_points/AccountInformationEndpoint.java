package com.informatixinc.calnotify.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.AuthMap;

@Path("/accountinformation")
public class AccountInformationEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User accountInformation(Session session){
		UserDao userDao = new UserDao();
		return userDao.getAccountInformation(AuthMap.getUserName(session.getSession()));
	}
}
