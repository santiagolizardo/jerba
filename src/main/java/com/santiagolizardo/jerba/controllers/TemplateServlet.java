package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

import org.apache.velocity.VelocityContext;

public class TemplateServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getParameter("fileName");
		String contentType = req.getParameter("contentType");

		if (null == fileName || null == contentType) {
			sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String cacheKey = "template-" + fileName;
		String output = null;
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			ConfigManager configManager = ConfigManager.getInstance();

			VelocityContext context = prepareContext(req);
			context.put("config", configManager);

			output = generateTemplate(fileName, context);
		}

		writeResponse(output, resp, contentType);
	}
}
