package com.santiagolizardo.jerba.utilities;

public class StringUtils {

	public static final String decodeHtmlEntities(String html) {
		return html.replaceAll("<", "&lt;");
	}

	public static String sanitize(String text) {
		if (null == text)
			return "";

		text = text.trim();
		text = text.toLowerCase();
		text = text.replaceAll("[^a-z0-9- ]", "");
		text = text.replaceAll("\\s+", "-");
		return text;
	}

	public static String capitalizeFirst(String text) {
		return text.substring(0, 1).toUpperCase().concat(text.substring(1));
	}

	public static String[] tokenize(String text) {
		String[] tokens = text.split("\\s*,\\s*");
		for (String token : tokens) {
			token = token.trim().toLowerCase();
		}
		return tokens;
	}
}
