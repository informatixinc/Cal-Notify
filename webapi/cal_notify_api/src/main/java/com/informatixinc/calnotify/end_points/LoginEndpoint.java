package com.informatixinc.calnotify.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.User;

import io.swagger.annotations.Api;

@Api(tags = {"login"})
@Path("/login")
public class LoginEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Session login(User login){
		
		Session session = new Session();
		
		UserDao userDao = new UserDao();
		return userDao.login(login, session);
	}
}
