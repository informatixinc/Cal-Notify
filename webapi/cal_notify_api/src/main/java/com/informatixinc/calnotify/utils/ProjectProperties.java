package com.informatixinc.calnotify.utils;

import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {
	
	private static Properties projectProperties = new Properties();

	public static void init() {
		try {
			projectProperties.load(ProjectProperties.class.getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Unable to load project properties");
		}
	}
	
	public static String getProperty(String propertyName){
		return projectProperties.getProperty(propertyName);
	}
}