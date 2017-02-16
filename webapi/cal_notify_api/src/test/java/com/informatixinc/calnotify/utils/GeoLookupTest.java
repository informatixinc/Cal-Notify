package com.informatixinc.calnotify.utils;

import org.junit.Test;

import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.model.User;
import com.informatixinc.calnotify.utils.GeoLookup;
import com.informatixinc.calnotify.utils.ProjectProperties;

public class GeoLookupTest {
	
	@Test
	public void lookupAddressTest(){
		ProjectProperties.init();
		User user = new User();
		user.setAddressOne("2485 Natomas Park Drive");
		user.setCity("Sacramento");
		user.setState("CA");
		GeoLookup geoLookup = new GeoLookup();
		Point point = geoLookup.latLongFromAddress(user);
		assert(point != null);
	}
}
