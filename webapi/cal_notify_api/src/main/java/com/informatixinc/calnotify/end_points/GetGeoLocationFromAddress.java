package com.informatixinc.calnotify.end_points;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.utils.GeoLookup;

import io.swagger.annotations.Api;

@Api(tags = {"geofromaddress"})
@Path("geofromaddress")
public class GetGeoLocationFromAddress {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Point getNotifications(Address address){
		GeoLookup geoLookup = new GeoLookup();
		Point point =  geoLookup.latLongFromAddress(address);
		if(point == null){
			point.getErrorResponse().setError(true);
			point.getErrorResponse().setErrorMessage("Address not found");
		}
		
		return point;
	}

}
