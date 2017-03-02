package com.informatixinc.calnotify.end_points;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.UserDao;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.GeoLookup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = {"addlocations"})
@Path("/addlocations")
public class AddLocationsEndPoint {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add user locations")
	public PutResponse addlocations(@ApiParam(value = "Object holding user info", required = true) User addlocations,
			@Context HttpServletRequest req) {
		PutResponse putResponse = new PutResponse();
		validate(addlocations, putResponse);
		if (putResponse.getErrorResponse().isError()) {
			return putResponse;
		}

		UserDao userDao = new UserDao();
		return userDao.addlocations(addlocations, putResponse, req.getHeader("Authorization"));
	}
	
	private PutResponse validate(User addlocations, PutResponse putResponse){
		if(addlocations.getAddresses().get(0).getAddressOne().length() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Missing Address Line One");
			return putResponse;
		}
		if(addlocations.getAddresses().get(0).getZipCode().length() == 0){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Missing Zip Code");
			return putResponse;
		}
		
		GeoLookup geoLookup = new GeoLookup();
		Point coordinates = geoLookup.latLongFromAddress(addlocations.getAddresses().get(0));
		
		if(coordinates == null){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Street address could not be located");
			return putResponse;
		}
		
		addlocations.setLocation(coordinates);
		
		return putResponse;
	}
}
