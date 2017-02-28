package com.informatixinc.calnotify.utils;

import org.junit.Test;

public class PushServiceTest {
	
	@Test
	public void sendTestMessage(){
		ProjectProperties.init();
		PushService pushService = new PushService();
		pushService.push("dL1YVfvrcdQ:APA91bEmHWvlQBsZkbtCnR3SVpS4lZJ5KeWd4kPZVV8yYakJNgRTUuXrntx86pg3Pcg5AouCdjYCYoRSHVDC3NMBq0rL6pSdjLLd4EpmV7AM3QHSWDIM3GT10oJ06rhDKyzh0zCJPZur", 
				"Push Notification Test Body", "Push Notification Test Title");
	}
}
