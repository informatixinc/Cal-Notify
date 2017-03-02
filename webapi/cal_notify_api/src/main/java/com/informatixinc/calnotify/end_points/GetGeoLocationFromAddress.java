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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = { "geofromaddress" })
@Path("geofromaddress")
public class GetGeoLocationFromAddress {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find the geographic coordinates of an address")
	public Point getNotifications(
			@ApiParam(value = "Object holding address info", required = true) Address address) {
		GeoLookup geoLookup = new GeoLookup();
		Point point = geoLookup.latLongFromAddress(address);
		if (point == null) {
			Point p = new Point();
			p.getErrorResponse().setError(true);
			p.getErrorResponse().setErrorMessage("Address not found");
		}

		return point;
	}

}
