package com.informatixinc.calnotify.end_points;

import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import com.informatixinc.calnotify.model.AdminMessage;
import com.informatixinc.calnotify.model.PutResponse;
import com.informatixinc.calnotify.utils.AuthMap;

import static org.junit.Assert.*;

public class AdminMessageTest {
	
	@Test
	public void adminMessage(){
		
		HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
		when(httpServletRequest.getHeader("Authorization")).thenReturn(AuthMap.getSessionFromEmail("testsuite@informatixinc.com"));
		AdminMessage message = new AdminMessage();
		message.setExpirationDate(new Date().getTime());
		message.setMessage("Unit Test Message");
		message.setTitle("Unit Test Title");
		message.setSentBy("testsuite@informatixinc.com");
		
		AdminMessageEndpoint adminMessageEndpoint = new AdminMessageEndpoint();
		PutResponse putResponse = adminMessageEndpoint.sendMessage(message, httpServletRequest);
		assertFalse(putResponse.getErrorResponse().isError());
	}

}
