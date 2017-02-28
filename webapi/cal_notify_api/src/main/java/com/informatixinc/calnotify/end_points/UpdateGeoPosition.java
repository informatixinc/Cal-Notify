package com.informatixinc.calnotify.end_points;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.informatixinc.calnotify.dao.GeoDao;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;

import io.swagger.annotations.Api;

@Api(tags = {"updateuserposition"})
@Path("/updateuserposition")
public class UpdateGeoPosition {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PutResponse updatePosition(Point point,  @Context HttpServletRequest req){
		GeoDao geoDao = new GeoDao();
		return geoDao.updatePosition(point, req.getHeader("Authorization"));
	}

}
