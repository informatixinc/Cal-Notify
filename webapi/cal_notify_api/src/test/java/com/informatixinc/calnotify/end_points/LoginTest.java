package com.informatixinc.calnotify.end_points;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.User;
import static org.junit.Assert.*;
public class LoginTest {
	
	//All passwords will be sha1 hashed 1k times by the client.  Replicating here for test.
	
	@Test
	public void loginTest(){
		User login = new User();
		login.setEmail("resident@norcal.informatixinc.com");
		
		String password = "Welcome101!";
		
		login.setPassword(password);
		
		LoginEndpoint loginEndPoint = new LoginEndpoint();
		Session session = loginEndPoint.login(login);
		
		assertTrue (session.getErrorResponse().isError());
		
		for (int i = 0; i < 2000; i++) {
			password = DigestUtils.sha512Hex(password);
		}
		
		session = loginEndPoint.login(login);
		
		
	}

}
