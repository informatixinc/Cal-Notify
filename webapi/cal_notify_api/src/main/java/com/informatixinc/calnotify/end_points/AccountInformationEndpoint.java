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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Contact;
import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@SwaggerDefinition(
        info = @Info(
                description = "Cal Notify back end service API",
                version = "V1.0.0",
                title = "Cal Notify Service API",
                contact = @Contact(
                   name = "Paul Ortiz", 
                   email = "paul.ortiz@informatixinc.com"
                ),
                license = @License(
                   name = "MIT", 
                   url = "https://opensource.org/licenses/MIT"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Private", description = "Tag used to denote operations as private")
        }, 
        externalDocs = @ExternalDocs(value = "Cal Notify", url = "http://calnotify.informatixinc.com")
)
@Api(tags = { "accountinformation" })
@Path("/accountinformation")
public class AccountInformationEndpoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Fetch account information")
	public User accountInformation(
			@ApiParam(value = "Object holding user session data", required = true) Session session) {
		UserDao userDao = new UserDao();
		return userDao.getAccountInformation(AuthMap.getUserName(session.getSession()));
	}
}
