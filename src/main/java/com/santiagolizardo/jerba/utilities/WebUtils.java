package com.santiagolizardo.jerba.utilities;

public class WebUtils {

	private static WebUtils singleton;
	
	public static WebUtils getInstance() {
		if(singleton == null)
			singleton = new WebUtils();
		return singleton;
	}
	
	public String sanitizeURL(String url) {
		if(url == null)
			return null;
		url = url.toLowerCase();
		url = url.replaceAll("[^a-z0-9\\s]", "");
		url = url.replaceAll("\\s+", "-");
		return url;
	}
}
