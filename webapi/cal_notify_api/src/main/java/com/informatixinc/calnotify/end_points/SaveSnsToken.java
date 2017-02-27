package com.informatixinc.calnotify.end_points;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.PutResponse;

@Path("/savesnstoken")
public class SaveSnsToken {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse updatePosition(String token, @Context HttpServletRequest req) {
		UserDao userDao = new UserDao();
		return userDao.saveSnsToken(token, req.getHeader("Authorization"));
	}
}
