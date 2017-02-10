package com.informatixinc.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.informatixinc.dao.DatabasePool;
import com.informatixinc.utils.ProjectProperties;

public class ApplicationLifecycleListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ProjectProperties.init();
//		DatabasePool.init();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		DatabasePool.close();
		
	}

}
