package com.informatixinc.calnotify.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DatabaseUtils {
	
	private static final Logger logger = Logger.getLogger(DatabaseUtils.class);
	
	public static void safeClose(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error("Unable to close result set", e);
		} finally {
			safeClose(conn, ps);
		}
	}
	
	public static void safeClose(PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
		} catch (SQLException e) {
			logger.error("Unable to close result set", e);
		}
	}
		
	public static void safeClose(Connection conn, PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			logger.error("Unable to close prepared statement", e);
		} finally {
			safeClose(conn);
		}
	}
	
	public static void safeClose(Connection conn) {
		try {
			if (conn != null) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Unable to close connection", e);
		}
	}
	
	public static void emptyToNull(int index, String emptyToNull, PreparedStatement ps){
		try {
			if(emptyToNull.length() > 0){
				ps.setString(index++, emptyToNull);
			}else{
				ps.setNull(index++, java.sql.Types.VARCHAR);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error adding null item to prepared statement");
		}
	}

}
