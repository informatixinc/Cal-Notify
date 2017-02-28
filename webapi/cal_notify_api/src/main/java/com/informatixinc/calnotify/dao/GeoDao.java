package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.utils.AuthMap;
import com.informatixinc.calnotify.utils.DatabaseUtils;

public class GeoDao {
	
	public PutResponse updatePosition(Point point, String session){
		PutResponse putResponse = new PutResponse();
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select id from public.user where email = ?");
			ps.setString(1, AuthMap.getUserName(session));
			rs = ps.executeQuery();
			if(rs.next()){
				int userId = rs.getInt("id");
				//Check if the user has a geo location already set.
				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement("select id from public.user_location where user_id = ? and address_id is null");
				ps.setInt(1, userId);
				rs = ps.executeQuery();
				if(rs.next()){
					int locationId = rs.getInt("id");
					DatabaseUtils.safeClose(ps,rs);
					ps = conn.prepareStatement("update public.user_location set location = POINT(?,?) where id = ?");
					ps.setDouble(1, point.getLongitude());
					ps.setDouble(2, point.getLatitude());
					ps.setInt(3, locationId);
					ps.executeUpdate();
				}else{
					DatabaseUtils.safeClose(ps,rs);
					ps = conn.prepareStatement("insert into public.user_location (location, user_id, nick_name) values (point(?,?),?,?)", Statement.RETURN_GENERATED_KEYS);
					ps.setDouble(1, point.getLongitude());
					ps.setDouble(2, point.getLatitude());
					ps.setInt(3, userId);
					ps.setString(4, "**Geo Location");
					if(ps.executeUpdate() == 1){
						DatabaseUtils.safeClose(ps,rs);
						ps = conn.prepareStatement("select id from public.user_location where user_id = ? order by id desc limit 1");
						ps.setInt(1, userId);
						rs = ps.executeQuery();
						rs.next();
												
						int locationId = rs.getInt("id");
						DatabaseUtils.safeClose(ps,rs);
						ps = conn.prepareStatement("insert into public.notification_settings (user_location_id, sms,email,push_notification) values (?, true, true, false)");
						ps.setInt(1, locationId);
						ps.executeUpdate();
					}
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return putResponse;
	}

}
