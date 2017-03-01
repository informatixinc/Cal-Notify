package com.informatixinc.calnotify;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.end_points.GetNotificationTest;
import com.informatixinc.calnotify.end_points.NotificationSettingsTest;
import com.informatixinc.calnotify.end_points.RegistrationTest;
import com.informatixinc.calnotify.utils.ProjectProperties;
import com.informatixinc.calnotify.utils.PushServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ PushServiceTest.class})
public class TestSuiteRunner {
//	@SuiteClasses({ RegistrationTest.class, LoginTest.class, GetNotificationTest.class, NotificationSettingsTest.class})
	
	@BeforeClass 
    public static void oneTimeSetUp() {
		ProjectProperties.init();
		DatabasePool.init();      
    }
	
	@AfterClass 
	public static void tearDownClass() { 
		DatabasePool.close();
    }

}
