package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.cache.Cache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

@SuppressWarnings("unchecked")
public class SitemapHtmlServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Cache cache = CacheSingleton.getInstance().getCache();

		ConfigManager configManager = ConfigManager.getInstance();

		String output = null;
		String cacheKey = "page-sitemap";
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			VelocityContext context = prepareContext(req);
			context.put(
					"pageTitle",
					"Site structure "
							+ configManager
									.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX));
			output = generateTemplate("sitemap.vm", context);
			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
