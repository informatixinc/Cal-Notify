package com.informatixinc.calnotify.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import com.informatixinc.calnotify.dao.DatabasePool;

public class PushServiceTest {
	
	@Test
	public void sendTestMessage(){
		
		for(String token : getTokens()){
			PushService pushService = new PushService();
			pushService.push(token, "Test Title", "Test Message Body");
		}
	}
	
	private ArrayList<String> getTokens(){
		Connection conn = DatabasePool.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<String> tokens = new ArrayList<String>();
		
		try {
			ps = conn.prepareStatement("select sns_token from public.sns_token");
			
			rs = ps.executeQuery();
			while(rs.next()){
				tokens.add(rs.getString("sns_token"));
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL error statement is " + ps.toString());
		}finally{
			DatabaseUtils.safeClose(conn, ps, rs);
		}
		
		return tokens;
	}
}
