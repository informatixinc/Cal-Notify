package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.informatixinc.calnotify.model.NotificationTransmission;
import com.informatixinc.calnotify.utils.DatabaseUtils;

/**
 * Manage persistence of transmission objects.
 * @author Paul
 *
 */
public class NotificationTransmissionDao {

	/**
	 * Insert a new transmission record
	 * @param transmission
	 */
	public void addNew(NotificationTransmission transmission) {
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into public.notification_transmission ");
		sql.append(" (user_id, notification_id, send_time, transmission_type) ");
		sql.append(" values(?, ?, ?, ?) ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, transmission.getUserId());
			ps.setInt(2, transmission.getNotificationId());
			Timestamp ts = new Timestamp(transmission.getSendTime().getTime());
			ps.setTimestamp(3, ts);
			ps.setInt(4, transmission.getTransmissionTypeId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("sql_error", e);
		} finally {
			DatabaseUtils.safeClose(conn, ps);
		}
	}
}
