package com.informatixinc.calnotify.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.utils.ProjectProperties;

/**
 * Listen for lifecycle events
 * 
 * @author Paul
 *
 */
public class ApplicationLifecycleListener implements ServletContextListener {

	@Override
	/**
	 * {@inheritDoc} Initialize props and db
	 */
	public void contextInitialized(ServletContextEvent sce) {
		ProjectProperties.init();
		DatabasePool.init();

	}

	@Override
	/**
	 * {@inheritDoc} closes db pool
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		DatabasePool.close();

	}

}
