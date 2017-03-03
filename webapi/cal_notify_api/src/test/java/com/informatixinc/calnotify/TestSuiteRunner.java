package com.informatixinc.calnotify;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.end_points.AccountInformationTest;
import com.informatixinc.calnotify.end_points.AccountsReportTest;
import com.informatixinc.calnotify.end_points.AddLocationsTest;
import com.informatixinc.calnotify.end_points.AdminHistoryTest;
import com.informatixinc.calnotify.end_points.AdminMessageReportTest;
import com.informatixinc.calnotify.end_points.AdminMessageTest;
import com.informatixinc.calnotify.end_points.GetActiveNotificationsTest;
import com.informatixinc.calnotify.end_points.GetNotificationByIdTest;
import com.informatixinc.calnotify.end_points.GetNotificationSettingsTest;
import com.informatixinc.calnotify.end_points.GetNotificationTest;
import com.informatixinc.calnotify.end_points.GetUserNotificationsTest;
import com.informatixinc.calnotify.end_points.NotificationSettingsTest;
import com.informatixinc.calnotify.end_points.RegistrationTest;
import com.informatixinc.calnotify.model.AccountType;
import com.informatixinc.calnotify.utils.AuthMap;
import com.informatixinc.calnotify.utils.ProjectProperties;
import com.informatixinc.calnotify.utils.PushServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ GetUserNotificationsTest.class})
public class TestSuiteRunner {
//	@SuiteClasses({ RegistrationTest.class, LoginTest.class, GetNotificationTest.class, NotificationSettingsTest.class})
	
	@BeforeClass 
    public static void setup() {
		ProjectProperties.init();
		DatabasePool.init();   
		AuthMap.addLogin("testsuite@informatixinc.com", AccountType.ADMIN.getAccountType());
    }
	
	@AfterClass 
	public static void tearDown() { 
		DatabasePool.close();
    }

}
