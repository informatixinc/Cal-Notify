package com.informatixinc.calnotify.utils;

import java.util.ArrayList;

import org.junit.Test;

import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.GeoLookup;
import com.informatixinc.calnotify.utils.ProjectProperties;

public class GeoLookupTest {
	
	@Test
	public void lookupAddressTest(){
		ProjectProperties.init();
		User user = new User();
		user.setAddresses(new ArrayList<Address>());
		user.getAddresses().add(new Address());
		user.getAddresses().get(0).setAddressOne("2485 Natomas Park Drive");
		user.getAddresses().get(0).setCity("Sacramento");
		user.getAddresses().get(0).setState("CA");
		GeoLookup geoLookup = new GeoLookup();
		Point point = geoLookup.latLongFromAddress(user);
		assert(point != null);
	}
}
