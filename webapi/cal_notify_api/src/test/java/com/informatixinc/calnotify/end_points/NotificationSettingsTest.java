package com.informatixinc.calnotify.end_points;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.dao.NotificationDao;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.utils.DatabaseUtils;

public class NotificationSettingsTest {
	
	@Test
	public void notificaitonTest(){
		int userLocationId = getFirstUserLocation();
		NotificationDao notificationDao = new NotificationDao();
		NotificationSettings notificationSettings = new NotificationSettings();
		
		notificationSettings.setUserLocationId(userLocationId);
		notificationSettings.setEmail(true);
		notificationSettings.setSms(true);
		notificationSettings.setSns(true);
		
		PutResponse response = notificationDao.addNotificationSettings(notificationSettings);
		assert(!response.getErrorResponse().isError());
	}
	
	private int getFirstUserLocation(){
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select id from public.user_location order by id desc limit 1");
			
			rs = ps.executeQuery();
			if(rs.next()){
				return rs.getInt("id");
			}else{
				throw new RuntimeException("No notifications to test with");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString());
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
	}

}
