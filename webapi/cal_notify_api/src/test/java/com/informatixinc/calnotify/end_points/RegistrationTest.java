package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import com.informatixinc.calnotify.model.Address;
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
			password = DigestUtils.sha512Hex(password);
		}
		
		user.setAddresses(new ArrayList<Address>());
		user.getAddresses().add(new Address());
		user.getAddresses().get(0).setAddressOne("2485 Natomas Park Drive");
		user.getAddresses().get(0).setAddressTwo("Suite 430");
		user.getAddresses().get(0).setCity("Sacramento");
		user.setEmail("resident@norcal.informatixinc.com");
		user.setFirstName("Raul");
		user.setLastName("Ocazionez");
		user.setPassword(password);
		user.setPhoneNumber("555-555-5555");
		user.getAddresses().get(0).setState("CA");
		user.getAddresses().get(0).setZipCode("95833");
		
		session = rep.register(user);
		
		assert(!session.getErrorResponse().isError());
	}

}
