package com.informatixinc.calnotify.utils;

import org.junit.Test;

public class PushServiceTest {
	
	@Test
	public void sendTestMessage(){
		ProjectProperties.init();
		PushService pushService = new PushService();
		pushService.push("dHkutJOaJF0:APA91bFzK_TxayDavXpG8e9e7fC8JuCdOO-GD1DiDmb3hyCuwXaQhHwJLPfjDn1o2SZLbgQzGDmzLbdZgRwLQXXBCnSSj9MeWzRgTAUp0u5iUcwH0ATIvZKEOwaNgIdHBJmRGPoDo1w_", 
				"Message Body", "Message Title");
	}
}
