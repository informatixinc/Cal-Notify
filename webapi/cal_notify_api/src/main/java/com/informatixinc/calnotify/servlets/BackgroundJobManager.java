package com.informatixinc.calnotify.servlets;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.informatixinc.calnotify.jobs.FetchNotificationsJob;

@WebListener
/**
 * Job Scheduler
 * 
 * @author Paul Ortiz
 *
 */
public class BackgroundJobManager implements ServletContextListener {

	private ScheduledExecutorService scheduler;

	@Override
	/**
	 * {@inheritDoc} starts the jobs
	 */
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new FetchNotificationsJob(), 0, 1, TimeUnit.HOURS);
	}

	@Override
	/**
	 * {@inheritDoc} stops the jobs
	 */
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}

}
