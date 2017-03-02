package com.informatixinc.calnotify.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.AdminDao;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.UserReport;
import com.informatixinc.calnotify.utils.AuthMap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "accountreport" })
@Path("/accountreport")
public class AccountsReportEndpoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Fetch user usage statistics")
	public UserReport getAccountReport(
			@ApiParam(value = "Object holding user session data", required = true) Session session) {

		if (!AuthMap.isAdmin(session.getSession())) {
			PutResponse putResponse = new PutResponse();
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Not authorized");
			return new UserReport();
		}

		AdminDao adminDao = new AdminDao();
		return adminDao.getUserReport();
	}

}
