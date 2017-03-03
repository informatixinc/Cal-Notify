package com.informatixinc.calnotify.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Util to manage project properties
 * 
 * @author Paul Ortiz
 *
 */
public class ProjectProperties {

	private static Properties projectProperties = new Properties();

	/**
	 * Load the project properties
	 */
	public static void init() {
		try {
			projectProperties.load(ProjectProperties.class.getClassLoader().getResourceAsStream("project.properties"));
			projectProperties
					.load(ProjectProperties.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Unable to load project properties");
		}
	}

	/**
	 * Get a property
	 * 
	 * @param propertyName
	 * @return - the property value
	 */
	public static String getProperty(String propertyName) {
		return projectProperties.getProperty(propertyName);
	}

	/**
	 * Get a property
	 * 
	 * @param propertyName
	 * @param defaultValue
	 * @return - the value or default
	 */
	public static int getProperty(String propertyName, int defaultValue) {
		try {
			return Integer.parseInt(ProjectProperties.getProperty("app_maxDistanceInMiles"));
		} catch (NumberFormatException e) {
			logger.error(e);
		}
		return defaultValue;
	}

	/**
	 * Get all the properties
	 * 
	 * @return - the props
	 */
	public static Properties getProperties() {
		return projectProperties;
	}

	private static final Logger logger = Logger.getLogger(ProjectProperties.class);
}