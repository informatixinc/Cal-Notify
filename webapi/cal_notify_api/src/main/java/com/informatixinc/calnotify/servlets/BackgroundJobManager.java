package com.informatixinc.calnotify.servlets;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.informatixinc.calnotify.jobs.FetchNotificationsJob;

@WebListener
public class BackgroundJobManager implements ServletContextListener {

	private ScheduledExecutorService scheduler;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new FetchNotificationsJob(), 0, 1, TimeUnit.HOURS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}

}
