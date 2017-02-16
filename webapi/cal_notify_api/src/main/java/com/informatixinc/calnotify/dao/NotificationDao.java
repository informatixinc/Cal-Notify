package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.NotificationSettings;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.utils.DatabaseUtils;

public class NotificationDao {
	
	public ArrayList<Notification> getActiveNotifications(){
		
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select notification.id as notification_id, type, issue_time, expire_time, icon from public.notification, public.notification_type "
					+ "where notification.notification_type = notification_type.id and expire_time > now()");
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				Notification notification = new Notification();
				notification.setTitle(rs.getString("type"));
				notification.setIssuetime(new Date(rs.getTimestamp("issue_time").getTime()));
				notification.setExpireTime(new Date(rs.getTimestamp("expire_time").getTime()));
				notification.setImageUrl(rs.getString("icon"));
				notification.setNotificationId(rs.getInt("notification_id"));
				
				notifications.add(notification);
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString());
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return notifications;
	}
	
	public Notification getNotificationById(int id){
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Notification notification = new Notification();
		
		try {
			ps = conn.prepareStatement("select notification.id as notification_id, type, issue_time, expire_time, icon from public.notification, public.notification_type "
					+ "where notification.notification_type = notification_type.id and notification.id = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				notification.setTitle(rs.getString("type"));
				notification.setIssuetime(new Date(rs.getTimestamp("issue_time").getTime()));
				notification.setExpireTime(new Date(rs.getTimestamp("expire_time").getTime()));
				notification.setImageUrl(rs.getString("icon"));
				notification.setNotificationId(rs.getInt("notification_id"));
			}else{
				notification.getErrorResponse().setError(true);
				notification.getErrorResponse().setErrorMessage("Unable to locate notification");
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString());
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return notification;
	}
	
	public PutResponse setNotificationSettings(NotificationSettings settings){
		PutResponse putResponse = new PutResponse();
		
		
		
		return putResponse;
	}

}
