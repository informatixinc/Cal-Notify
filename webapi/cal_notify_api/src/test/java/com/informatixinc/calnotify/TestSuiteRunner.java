package com.informatixinc.calnotify;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.end_points.LoginTest;
import com.informatixinc.calnotify.end_points.NotificationTest;
import com.informatixinc.calnotify.end_points.RegistrationTest;
import com.informatixinc.calnotify.utils.ProjectProperties;

@RunWith(Suite.class)
@SuiteClasses({ NotificationTest.class})
public class TestSuiteRunner {
//	@SuiteClasses({ RegistrationTest.class, LoginTest.class})
	
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
