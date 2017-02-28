package com.informatixinc.calnotify.end_points;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.validator.routines.EmailValidator;

import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.GeoLookup;

import io.swagger.annotations.Api;

@Api(tags = {"register"})
@Path("/register")
public class RegisterEndpoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Session register(User registration){
		
		Session session = new Session();
		
		validate(registration, session);
		if(session.getErrorResponse().isError()){
			return session;
		}
		
		UserDao userDao = new UserDao();
		return userDao.register(registration, session);
	}
	
	private Session validate(User registration, Session session){
		EmailValidator validator = EmailValidator.getInstance();
		if(!validator.isValid(registration.getEmail())){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Error with email address");
			return session;
		}
		
		if(registration.getPassword().length() == 0){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Missing Password");
			return session;
		}
		
		if(registration.getAddresses().get(0).getAddressOne().length() == 0){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Missing Address Line One");
			return session;
		}
		
		if(registration.getAddresses().get(0).getCity().length() == 0){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Missing City");
			return session;
		}
		
		if(registration.getAddresses().get(0).getState().length() == 0){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Missing State");
			return session;
		}
		
		if(registration.getAddresses().get(0).getZipCode().length() == 0){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Missing Zip Code");
			return session;
		}
		
		GeoLookup geoLookup = new GeoLookup();
		Point coordinates = geoLookup.latLongFromAddress(registration.getAddresses().get(0));
		
		if(coordinates == null){
			session.getErrorResponse().setError(true);
			session.getErrorResponse().setErrorMessage("Street address could not be located");
			return session;
		}
		
		registration.setLocation(coordinates);
		
		return session;
	}

}
