package com.informatixinc.calnotify.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.informatixinc.calnotify.dao.DatabasePool;
import com.informatixinc.calnotify.utils.ProjectProperties;

public class ApplicationLifecycleListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ProjectProperties.init();
		DatabasePool.init();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DatabasePool.close();
		
	}

}
