package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.UsState;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.AuthMap;
import com.informatixinc.calnotify.utils.SecurityUtils;

public class UserDao {
	
	public Session register(User user, Session session){
		
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		
		int index = 1;
		
		byte[] salt = SecurityUtils.getNewSalt();
		
		try {
			ps = conn.prepareStatement("insert into public.user (email, password, salt, last_login, first_name, last_name, "
					+ "phone_number, address_one, address_two, state_id, city, zip_code, signup_date)"
					+ "values (?,?,?,now(),?,?,?,?,?,?,?,?,now())");
			
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
			
			if(ps.executeUpdate() == 1){
				session.setSession(AuthMap.addLogin(user.getEmail()));
			}else{
				session.getErrorResponse().setError(true);
				session.getErrorResponse().setErrorMessage("Unknown error");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString());
		}
		
		return session;
	}

}
