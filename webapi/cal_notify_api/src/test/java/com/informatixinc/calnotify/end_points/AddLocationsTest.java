package com.informatixinc.calnotify.end_points;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.AuthMap;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class AddLocationsTest {
	
	@Test
	public void testAddLocations(){
		
		HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
		
		when(httpServletRequest.getHeader("Authorization")).thenReturn(AuthMap.getSessionFromEmail("testsuite@informatixinc.com"));
		
		User user = new User();
		user.setAddresses(new ArrayList<Address>());
		user.getAddresses().add(new Address());
		user.getAddresses().get(0).setAddressOne("2485 Natomas Park Drive");
		user.getAddresses().get(0).setAddressTwo("Suite 430");
		user.getAddresses().get(0).setCity("Sacramento");
		user.getAddresses().get(0).setState("CA");
		user.getAddresses().get(0).setZipCode("95833");
		
		AddLocationsEndPoint addLocationsEndPoint= new AddLocationsEndPoint();
		PutResponse putResponse = addLocationsEndPoint.addlocations(user, httpServletRequest);
		assertFalse(putResponse.getErrorResponse().isError());
	}

}
