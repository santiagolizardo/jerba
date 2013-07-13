package com.santiagolizardo.jerba.utilities;

public class WebUtils {

	private static WebUtils singleton;
	
	public static WebUtils getInstance() {
		if(singleton == null)
			singleton = new WebUtils();
		return singleton;
	}
	
	/**
	 * @deprecated
	 * @param url String
	 * @return String
	 */
	public String sanitizeURL(String url) {
		return StringUtils.sanitize(url);
	}
}
