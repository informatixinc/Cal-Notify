package com.informatixinc.calnotify.model;

import java.util.ArrayList;
import java.util.HashMap;

public class UserReport {
	
	private HashMap<Integer, HashMap<Integer, ArrayList<UserEvent>>> reportData = new HashMap<Integer, HashMap<Integer, ArrayList<UserEvent>>>();

	public HashMap<Integer, HashMap<Integer, ArrayList<UserEvent>>> getReportData() {
		return reportData;
	}

	public void setReportData(HashMap<Integer, HashMap<Integer, ArrayList<UserEvent>>> reportData) {
		this.reportData = reportData;
	}
	
	

}
