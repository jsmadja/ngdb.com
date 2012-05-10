package com.ngdb.service.loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loaders {

	public static String extract(String html, String pattern) {
		try {
			Matcher matcher = Pattern.compile(pattern).matcher(html);
			matcher.find();
			return matcher.group(1).trim();
		} catch (IllegalStateException e) {
			return "";
		}
	}

	public static Long extractAsLong(String html, String pattern) {
		Matcher matcher = Pattern.compile(pattern).matcher(html);
		if (matcher.find()) {
			return Long.valueOf(matcher.group(1));
		}
		return 0L;
	}
}
