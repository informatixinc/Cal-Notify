package com.informatixinc.calnotify.end_points;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.User;

public class RegistrationTest {
	
	 
	@Test
	public void registrationTest(){
		User user = new User();
		RegisterEndpoint rep = new RegisterEndpoint();
		Session session = rep.register(user);
		assert(session.getErrorResponse().isError());
		assert(session.getErrorResponse().getErrorMessage().equals("Error with email address"));
		
		String password = "Welcome101!";
		
		for (int i = 0; i < 1000; i++) {
			password = DigestUtils.sha1Hex(password);
		}
		
		user.setAddressOne("2485 Natomas Park Drive");
		user.setAddressTwo("Suite 430");
		user.setCity("Sacramento");
		user.setEmail("resident@norcal.informatixinc.com");
		user.setFirstName("Raul");
		user.setLastName("Ocazionez");
		user.setPassword(password);
		user.setPhoneNumber("555-555-5555");
		user.setState("CA");
		user.setZipCode("95833");
		
		session = rep.register(user);
		
		assert(!session.getErrorResponse().isError());
	}

}
