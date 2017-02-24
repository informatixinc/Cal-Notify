package com.informatixinc.calnotify.utils;

import org.junit.Test;

import com.informatixinc.calnotify.model.Address;
import com.informatixinc.calnotify.model.Point;
import com.informatixinc.calnotify.utils.GeoLookup;
import com.informatixinc.calnotify.utils.ProjectProperties;

public class GeoLookupTest {
	
	@Test
	public void lookupAddressTest(){
		ProjectProperties.init();
		Address address = new Address();
		address.setAddressOne("2485 Natomas Park Drive");
		address.setCity("Sacramento");
		address.setState("CA");
		GeoLookup geoLookup = new GeoLookup();
		Point point = geoLookup.latLongFromAddress(address);
		assert(point != null);
	}
}
