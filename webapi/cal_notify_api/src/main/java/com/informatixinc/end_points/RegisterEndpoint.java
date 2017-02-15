package com.informatixinc.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.dao.UserDao;
import com.informatixinc.model.PutResponse;
import com.informatixinc.model.User;

@Path("/register")
public class RegisterEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse register(User user){
		UserDao userDao = new UserDao();
		return userDao.register(user);
	}

}
