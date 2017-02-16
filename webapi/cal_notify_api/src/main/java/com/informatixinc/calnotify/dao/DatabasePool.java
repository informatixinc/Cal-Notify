package com.informatixinc.calnotify.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;

import com.informatixinc.calnotify.utils.ProjectProperties;


public class DatabasePool {
	
	private static PGPoolingDataSource source = new PGPoolingDataSource();
	private static final Logger logger = Logger.getLogger(DatabasePool.class);
	
	public static Connection getConnection(){
		try {
			return source.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("Error getting a connection from the database connection pool",e);
		}
	}
	
	public static void close(){
		logger.info("=====Stopping DB connection pool======");
		source.close();
	}
	
	public static void init(){
		
		source.setDataSourceName("source");
		source.setServerName(ProjectProperties.getProperty("db_url"));
		source.setPortNumber(Integer.parseInt(ProjectProperties.getProperty("db_port")));
		source.setDatabaseName(ProjectProperties.getProperty("db_name"));
		source.setUser(ProjectProperties.getProperty("db_user_name"));
		source.setPassword(ProjectProperties.getProperty("db_password"));
		source.setMaxConnections(50);

		logger.info("=====Starting DB connection pool======");
		try {
			source.getConnection();
			logger.info("=====Pool created======");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (source == null) {
				throw new RuntimeException("Unable to establish DB connetion");
			}
		}
	}

}
