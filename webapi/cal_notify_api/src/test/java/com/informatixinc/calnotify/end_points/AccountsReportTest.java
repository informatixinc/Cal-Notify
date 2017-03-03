package com.informatixinc.calnotify.end_points;

import java.util.ArrayList;

import java.util.Calendar;

import org.junit.Test;

import com.informatixinc.calnotify.model.Session;
import com.informatixinc.calnotify.model.UserEvent;
import com.informatixinc.calnotify.model.UserReport;
import com.informatixinc.calnotify.utils.AuthMap;

import static org.junit.Assert.*;

public class AccountsReportTest {
	
	@Test
	public void accountReport(){
		Calendar currentDate = Calendar.getInstance();
		Session session = new Session();
		session.setSession(AuthMap.getSessionFromEmail("testsuite@informatixinc.com"));
		AccountsReportEndpoint accountsReportEndpoint = new AccountsReportEndpoint();
		UserReport report = accountsReportEndpoint.getAccountReport(session);
		ArrayList<UserEvent> monthEvents = report.getReportData().get(currentDate.get(Calendar.YEAR)).get(currentDate.get(Calendar.MONTH));
		assertTrue (monthEvents.get(0).getCount() > 0);
	}

}
