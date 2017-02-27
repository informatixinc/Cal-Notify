package com.informatixinc.calnotify.utils;

import java.math.BigDecimal;

public class StringUtils {

	public static String nullToEmpty(String element) {
		if (element == null) {
			return "";
		}

		return element;
	}

	public static double parseDouble(String s) {
		final BigDecimal bd = new BigDecimal(s);
		return bd.doubleValue();
	}

	public static boolean isNull(String s) {
		return s == null || s.length() == 0;
	}

}
