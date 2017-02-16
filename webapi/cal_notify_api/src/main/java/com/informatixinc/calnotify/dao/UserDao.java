package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.UsState;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.AuthMap;
import com.informatixinc.calnotify.utils.DatabaseUtils;
import com.informatixinc.calnotify.utils.SecurityUtils;

public class UserDao {
	
	public Session register(User user, Session session){
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int index = 1;
		
		byte[] salt = SecurityUtils.getNewSalt();
		
		try {
			ps = conn.prepareStatement("insert into public.user (email, password, salt, last_login, first_name, last_name, "
					+ "phone_number, address_one, address_two, state_id, city, zip_code, signup_date, account_type)"
					+ "values (?,?,?,now(),?,?,?,?,?,?,?,?,now(),?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(index++, user.getEmail());
			ps.setBytes(index++, SecurityUtils.hashPassword(user.getPassword().getBytes(), salt));
			ps.setBytes(index++, salt);
			ps.setString(index++, user.getFirstName());
			ps.setString(index++, user.getLastName());
			ps.setString(index++, user.getPhoneNumber().replaceAll( "[^\\d]", "" ));
			ps.setString(index++, user.getAddressOne());
			ps.setString(index++, user.getAddressTwo());
			ps.setInt(index++, UsState.getStateId(user.getState()));
			ps.setString(index++, user.getCity());
			ps.setString(index++, user.getZipCode());
			//Defaulting this to a user account type for now
			ps.setInt(index++, 2);
			
			if(ps.executeUpdate() == 1){
				session.setSession(AuthMap.addLogin(user.getEmail()));
				rs = ps.getGeneratedKeys();
				rs.next();
				int userId = rs.getInt(1);
				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement("insert into public.user_locations (user_id, location) values (?,point(?,?))");
				ps.setInt(1, userId);
				ps.setDouble(2, user.getLocation().getLongitude());
				ps.setDouble(3, user.getLocation().getLatitude());
				ps.executeUpdate();
			}else{
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Unknown error");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString());
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return session;
	}
	
	public Session login(User login, Session session){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		byte[] salt;
			
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement("select salt from public.user where email = ?");
			ps.setString(1, login.getEmail());
			rs = ps.executeQuery();
			
			if(rs.next()){
				salt = rs.getBytes("salt");
			}else{
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Invalid email or password");
				return session;
			}
			
			DatabaseUtils.safeClose(ps, rs);
			
			ps = conn.prepareStatement("select id from public.user where email = ? and password = ?");
			ps.setString(1, login.getEmail());
			ps.setBytes(2, SecurityUtils.hashPassword(login.getPassword().getBytes(), salt));
			rs = ps.executeQuery();
			
			int userId;
			
			if(rs.next()){
				userId = rs.getInt("id");
			}else{
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Invalid email or password");
				return session;
			}
			
			DatabaseUtils.safeClose(ps, rs);
			
			session.setSession(AuthMap.addLogin(login.getEmail()));
			
		} catch (SQLException e) {
			throw new RuntimeException("sql error", e);
		} finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return session;
		
	}
	
}