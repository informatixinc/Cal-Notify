package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.informatixinc.calnotify.model.AdminMessage;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.NotificationClassification;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.UserReport;
import com.informatixinc.calnotify.utils.DatabaseUtils;

public class AdminDao {
	
	public PutResponse addMessage(AdminMessage message){
		PutResponse putResponse = new PutResponse();
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("insert into public.notification (classification_id, title, send_time, expire_time, admin_message_body, admin_sender_email) values (?,?,now(),?,?,?)");
			ps.setInt(1, NotificationClassification.AdminNotification.getId());
			ps.setString(2, message.getTitle());
			ps.setDate(3, new Date(message.getExpirationDate()));
			ps.setString(4, message.getMessage());
			ps.setString(5, message.getSentBy());
			ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return putResponse;
	}
	
	public ArrayList<AdminMessage> getAdminMessages(){
		ArrayList<AdminMessage> messages = new ArrayList<AdminMessage>();
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select title, expire_time, admin_message_body, admin_sender_email, admin_message_body from public.notification where classification_id = ?");
			ps.setInt(1, NotificationClassification.AdminNotification.getId());
			rs = ps.executeQuery();
			
			while(rs.next()){
				AdminMessage adminMessage = new AdminMessage();
				adminMessage.setExpirationDate(rs.getDate("expire_time").getTime());
				adminMessage.setMessage(rs.getString("admin_message_body"));
				adminMessage.setSentBy(rs.getString("admin_sender_email"));
				adminMessage.setMessage(rs.getString("admin_message_body"));
				adminMessage.setTitle(rs.getString("title"));
				messages.add(adminMessage);
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return messages;
	}
	
	public ArrayList<Notification> getNotificationsForReport(){
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		
		try {
			ps = conn.prepareStatement("select type as title, send_time, expire_time from public.notification, public.notification_classification where notification.classification_id = notification_classification.id");
			rs = ps.executeQuery();
			while(rs.next()){
				Notification notification = new Notification();
				notification.setTitle(rs.getString("title"));
				notification.setSendTime(rs.getTimestamp("send_time"));
				notification.setExpireTime(rs.getTimestamp("expire_time"));
				notifications.add(notification);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return notifications;
	}
	
	public UserReport getUserReport(){
		UserReport userReport = new UserReport();
		
		return userReport;
	}

}
