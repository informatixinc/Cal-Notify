package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.informatixinc.calnotify.model.AdminMessage;
import com.informatixinc.calnotify.model.Notification;
import com.informatixinc.calnotify.model.NotificationClassification;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.UserEvent;
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
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
						
			int firstYear = 2017;
			Calendar now = Calendar.getInstance(); 
			int currentYear = now.get(Calendar.YEAR);
			int currentMonth = now.get(Calendar.MONTH) + 1;
			
			for (int i = 0; i <= currentYear - firstYear; i++) {
				userReport.getReportData().put(i + firstYear, new HashMap<Integer, ArrayList<UserEvent>>());
				for (int j = 1; j <= 12; j++) {
					if(currentYear == i + firstYear && j > currentMonth){
						continue;
					}
					userReport.getReportData().get(i + firstYear).put(j, new ArrayList<UserEvent>());
					ps = conn.prepareStatement("select count(id) as new_users from public.user WHERE EXTRACT(MONTH FROM signup_date) = ? and EXTRACT(YEAR FROM signup_date) = ?");
					ps.setInt(1, j);
					ps.setInt(2, i + firstYear);
					rs = ps.executeQuery();
					rs.next();
					userReport.getReportData().get(i + firstYear).get(j).add(new UserEvent("new user", rs.getInt("new_users")));
					DatabaseUtils.safeClose(ps,rs);
					
					ps = conn.prepareStatement("select count(distinct(user_id)) as logins from public.user_login where EXTRACT(MONTH FROM date) = ? and EXTRACT(YEAR FROM date) = ?");
					ps.setInt(1, j);
					ps.setInt(2, i + firstYear);
					rs = ps.executeQuery();
					rs.next();
					int activeUsers = rs.getInt("logins");
					userReport.getReportData().get(i + firstYear).get(j).add(new UserEvent("active_users", activeUsers));
					
					DatabaseUtils.safeClose(ps,rs);
					
					ps = conn.prepareStatement("select count(id) as total_users from public.user where signup_date < ?");
					Calendar loopDate = Calendar.getInstance();
					if(j == 12){
						loopDate.set(i + firstYear + 1, 1, 1);
					}else{
						loopDate.set(i + firstYear, j, 1);
					}
					ps.setDate(1, new Date(loopDate.getTimeInMillis()));
					rs = ps.executeQuery();
					rs.next();
					int inactiveUsers = rs.getInt("total_users") - activeUsers;
					if(inactiveUsers < 0){
						inactiveUsers = 0;
					}
					userReport.getReportData().get(i + firstYear).get(j).add(new UserEvent("inactive users", inactiveUsers));
					DatabaseUtils.safeClose(ps,rs);
				}
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return userReport;
	}

}
