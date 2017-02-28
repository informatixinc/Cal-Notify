package com.informatixinc.calnotify.end_points;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.validator.routines.EmailValidator;

import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.GeoLookup;

import io.swagger.annotations.Api;

@Api(tags = {"updateaccount"})
@Path("/updateaccount")
public class UpdateAccountEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse updateAccount(User user, @Context HttpServletRequest req){
		PutResponse putResponse = new PutResponse();
		validate(user, putResponse);
		
		if(putResponse.getErrorResponse().isError()){
			return putResponse;
		}
		
		UserDao userDao = new UserDao();
		return userDao.updateAccount(user, putResponse, req.getHeader("Authorization"));
	}
	
	private PutResponse validate(User user, PutResponse putResponse){
		EmailValidator validator = EmailValidator.getInstance();
		if(!validator.isValid(user.getEmail())){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Error with email address");
			return putResponse;
		}
		
		if(user.getAddresses().get(0).getAddressOne().length() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Missing Address Line One");
			return putResponse;
		}
		
		if(user.getAddresses().get(0).getCity().length() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Missing City");
			return putResponse;
		}
		
		if(user.getAddresses().get(0).getState().length() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Missing State");
			return putResponse;
		}
		
		if(user.getAddresses().get(0).getZipCode().length() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Missing Zip Code");
			return putResponse;
		}
		
		GeoLookup geoLookup = new GeoLookup();
		Point coordinates = geoLookup.latLongFromAddress(user.getAddresses().get(0));
		
		if(coordinates == null){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Street address could not be located");
			return putResponse;
		}
		
		user.setLocation(coordinates);
		
		return putResponse;
	}

}
