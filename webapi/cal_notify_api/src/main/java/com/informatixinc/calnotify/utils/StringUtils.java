package com.informatixinc.calnotify.utils;

public class StringUtils {
	
	public static String nullToEmpty(String element){
		if(element == null){
			return "";
		}
		
		return element;
	}

}
