package com.informatixinc.calnotify.utils;

import java.math.BigDecimal;

/**
 * Util for working with strings
 * 
 * @author Paul
 *
 */
public class StringUtils {

	/**
	 * Change null string to empty
	 * 
	 * @param element
	 *            - the string
	 * @return an empty string if null or the string itself
	 */
	public static String nullToEmpty(String element) {
		if (element == null) {
			return "";
		}

		return element;
	}

	/**
	 * Parse a string as double
	 * 
	 * @param s
	 *            - the string
	 * @return - the double value
	 */
	public static double parseDouble(String s) {
		final BigDecimal bd = new BigDecimal(s);
		return bd.doubleValue();
	}

	/**
	 * Determines if the string is null
	 * 
	 * @param s
	 *            - the string
	 * @return - true if null
	 */
	public static boolean isNull(String s) {
		return s == null || s.length() == 0;
	}

}
