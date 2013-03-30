package com.santiagolizardo.jerba.utilities;

public class StringUtils {

	public static final String decodeHtmlEntities(String html) {
		return html.replaceAll("<", "&lt;");
	}
}
