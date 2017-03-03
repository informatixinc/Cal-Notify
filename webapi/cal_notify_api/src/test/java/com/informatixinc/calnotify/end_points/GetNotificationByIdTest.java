package com.informatixinc.calnotify.end_points;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.utils.DatabaseUtils;

import static org.junit.Assert.*;

public class GetNotificationByIdTest {
	
	@Test
	public void getNotificationById(){
		Notification latestNotification = getLatestNotification();
		GetNotificationByIdEndpoint getNotificationByIdEndpoint = new GetNotificationByIdEndpoint();
		Notification retreivedNotification = getNotificationByIdEndpoint.getNotifications(latestNotification);
		assertEquals(latestNotification.getTitle(), retreivedNotification.getTitle());
	}
	
	private Notification getLatestNotification(){
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Notification notification = new Notification();
		
		try {
			ps = conn.prepareStatement("select id, title from public.notification order by id desc limit 1");
			
			rs = ps.executeQuery();
			if(rs.next()){
				notification.setId(rs.getInt("id"));
				notification.setTitle(rs.getString("title"));
				return notification;
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
