package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.cache.Cache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

@SuppressWarnings("serial")
public class RobotsTxtServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String output = null;

		String cacheKey = "robots-txt";
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			String webSiteUrl = ConfigManager.getInstance().getValue(
					ConfigManager.WEBSITE_URL);

			VelocityContext context = prepareContext(req);
			context.put("webSiteUrl", webSiteUrl);
			output = generateTemplate("robots-txt.vm", context);
			cache.put(cacheKey, output);
		}

		writeResponse(output, resp, "text/plain; charset=utf-8");
	}
}
