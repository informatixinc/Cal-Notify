package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.UsState;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.model.UserNotification;
import com.informatixinc.calnotify.utils.AuthMap;
import com.informatixinc.calnotify.utils.DatabaseUtils;
import com.informatixinc.calnotify.utils.ProjectProperties;
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
					+ "phone_number, signup_date, account_type)"
					+ "values (?,?,?,now(),?,?,?,now(),?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(index++, user.getEmail());
			ps.setBytes(index++, SecurityUtils.hashPassword(user.getPassword().getBytes(), salt));
			ps.setBytes(index++, salt);
			ps.setString(index++, user.getFirstName());
			ps.setString(index++, user.getLastName());
			ps.setString(index++, user.getPhoneNumber().replaceAll( "[^\\d]", "" ));
			//Defaulting this to a user account type for now
			ps.setInt(index++, 2);
			
			if(ps.executeUpdate() == 1){
				session.setSession(AuthMap.addLogin(user.getEmail()));
				rs = ps.getGeneratedKeys();
				rs.next();
				int userId = rs.getInt(1);
				
				DatabaseUtils.safeClose(ps, rs);
				ps = conn.prepareStatement("insert into public.user_address (user_id, address_one, address_two, state_id, zip_code) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, userId);
				ps.setString(2, user.getAddress().getAddressOne());
				ps.setString(3, user.getAddress().getAddressTwo());
				ps.setInt(4, UsState.getStateId(user.getAddress().getState()));
				ps.setString(5, user.getAddress().getZipCode());
				ps.executeUpdate();
				
				rs = ps.getGeneratedKeys();
				rs.next();
				int addressId = rs.getInt(1);
				
				DatabaseUtils.safeClose(ps, rs);
				
				ps = conn.prepareStatement("insert into public.user_location (user_id, location, address_id) values (?,point(?,?),?)");
				ps.setInt(1, userId);
				ps.setDouble(2, user.getLocation().getLongitude());
				ps.setDouble(3, user.getLocation().getLatitude());
				ps.setInt(4, addressId);
				ps.executeUpdate();
				
			}else{
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Unknown error");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
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
			
			if(rs.next()){
				session.setSession(AuthMap.addLogin(login.getEmail()));
				return session;
			}else{
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Invalid email or password");
				return session;
			}
			
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
	}
	
	public PutResponse updateAccount(User user, PutResponse putResponse, String sessionId){
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String email = AuthMap.getUserName(sessionId);
		if(email == null){
			putResponse.getErrorResponse().setError(true);
			putResponse.getErrorResponse().setErrorMessage("Not logged in");
			return putResponse;
		}
		int locationId;
		int userId;
		
		try {
			//First address is their registration address
			ps = conn.prepareStatement("select user.id as user_id, user_location.id as location_id from public.user, public.user_location where public.user.id = user_location.user_id where user.email = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			ps.close();
			if(rs.next()){
				userId = rs.getInt("user_id");
				locationId = rs.getInt("location_id");
				DatabaseUtils.safeClose(ps, rs);
				StringBuilder sb = new StringBuilder();
				sb.append("update public.user set email = ?,");
				if(user.getPassword().length() > 0){
					sb.append("password = ?,salt = ?");
				}
				sb.append("first_name = ?, last_name = ?, phone_number = ?");
				sb.append("where public.user.id = ?");
				
				int index = 1;
				ps=conn.prepareStatement(sb.toString());
				ps.setString(index++, user.getEmail());
				if(user.getPassword().length() > 0){
					byte[] salt = SecurityUtils.getNewSalt();
					ps.setBytes(index++, SecurityUtils.hashPassword(user.getPassword().getBytes(), salt));
					ps.setBytes(index++, salt);
				}
				ps.setString(index++, user.getFirstName());
				ps.setString(index++, user.getLastName());
				ps.setString(index++, user.getPhoneNumber());
				ps.setInt(index++, userId);
				
				if(ps.executeUpdate() == 1){
					DatabaseUtils.safeClose(ps,rs);
					ps = conn.prepareStatement("update public.user_address set address_one = ?, address_two = ?, state_id = ?, nick_name = ? where id = ?");
					ps.setString(1, user.getAddress().getAddressOne());
					ps.setString(2, user.getAddress().getAddressTwo());
					ps.setInt(3, UsState.getStateId(user.getAddress().getState()));
					ps.setString(4, user.getAddress().getNickName());
					ps.setInt(5, locationId);
					
					if(ps.executeUpdate() == 1){
						return putResponse;
					}else{
						throw new RuntimeException("Error updating user address");
					}
					
				}else{
					throw new RuntimeException("Error updating user account");
				}
				
			}else{
				putResponse.getErrorResponse().setError(true);
				putResponse.getErrorResponse().setErrorMessage("Unable to locate primary address");
				return putResponse;
			}
			
			
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString(), e);
		} finally{
			DatabaseUtils.safeClose(conn, ps);
		}
	}

	public List<UserNotification> findUsersInProximityofEvent(Point point) {
		final List<UserNotification> userNotifications = new ArrayList<UserNotification>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final int MAXDISTANCEINMILES = ProjectProperties.getProperty("app_maxDistanceInMiles", 50);
		final StringBuilder sql = new StringBuilder();
		sql.append(" select u.id, u.first_name, u.last_name, u.email, u.phone_number, ns.sms, ns.email ");
		sql.append(" from public.user u ");
		sql.append(" inner join public.user_location ul on ul.user_id = u.id ");
		sql.append("	and (ul.location <@> POINT(?, ?)) < ? ");
		sql.append(" inner join public.notification_settings ns on ns.user_location_id = ul.id ");
		try {
			conn = DatabasePool.getConnection();
			ps = conn.prepareStatement(sql.toString());
			ps.setDouble(1, point.getLongitude());
			ps.setDouble(2, point.getLatitude());
			ps.setInt(3, MAXDISTANCEINMILES);
			rs = ps.executeQuery();
			while (rs.next()) {
				final int userId = rs.getInt(1);
				final String firstName = rs.getString(2);
				final String lastName = rs.getString(3);
				final String email = rs.getString(4);
				final String phoneNumber = rs.getString(5);
				final boolean sendSms = rs.getBoolean(6);
				final boolean sendEmail = rs.getBoolean(7);
				final UserNotification userNotification = new UserNotification();
				userNotification.setUserId(userId);
				userNotification.setFirstName(firstName);
				userNotification.setLastName(lastName);
				userNotification.setEmail(email);
				userNotification.setPhoneNumber(phoneNumber);
				userNotification.setSendSms(sendSms);
				userNotification.setSendEmail(sendEmail);
				userNotifications.add(userNotification);
			}
		} catch (SQLException e) {
			throw new RuntimeException("sql error", e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		return userNotifications;
	}
	
	public User getAccountInformation(String email){
		User user = new User();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			ps = conn.prepareStatement("select first_name, last_name, address_one, address_two, state_id, zip_code, email, phone_number "
					+ "from public.user, public.user_address where email = ? and public.user.id = user_address.user_id limit 1");
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				user.setAddress(new Address());
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.getAddress().setAddressOne(rs.getString("address_one"));
				user.getAddress().setAddressTwo(rs.getString("address_two"));
				user.getAddress().setCity(rs.getString("city"));
				user.getAddress().setState(UsState.getStateName(rs.getInt("state_id")));
				user.getAddress().setZipCode(rs.getString("zip_code"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("phone_number"));
				
			}else{
				throw new RuntimeException("User not found");
			}
		} catch (SQLException e) {
			throw new RuntimeException("sql error", e);
		} finally {
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return user;
	}
}