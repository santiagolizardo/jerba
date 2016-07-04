package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.utilities.HtmlUtils;
import com.santiagolizardo.jerba.utilities.UrlFactory;
import com.santiagolizardo.jerba.utilities.WebUtils;
import com.santiagolizardo.jerba.utilities.templates.DatastoreResourceLoader;

public class Command {

	private static final long serialVersionUID = 2570210304300019515L;

	protected static final Logger logger = Logger.getLogger(BaseServlet.class
			.getName());

	private VelocityEngine velocityEngine;

	public void init() {

		DatastoreResourceLoader datastoreResourceLoader = new DatastoreResourceLoader();

		velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "datastore");
		velocityEngine.setProperty("datastore.resource.loader.instance",
				datastoreResourceLoader);

		try {
			velocityEngine.init();
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	protected VelocityContext prepareContext(HttpServletRequest request) {
		ConfigManager configManager = ConfigManager.getInstance();

		String serverName = request.getServerName();
		VelocityContext context = new VelocityContext();
		context.put("config", configManager);
		context.put("urlFactory", UrlFactory.getInstance());
		context.put("webUtils", WebUtils.getInstance());
		context.put("serverName", serverName);
		context.put("esc", new EscapeTool());
		context.put("tools", new HtmlUtils());
		context.put("pages", ArticleManager.getInstance().findByType(ArticleType.Permanent));

		String metaTitle = configManager.getValue(ConfigManager.META_TITLE);
		String metaKeywords = configManager
				.getValue(ConfigManager.META_KEYWORDS);
		String metaDesc = configManager
				.getValue(ConfigManager.META_DESCRIPTION);

		context.put("pageTitle", metaTitle);
		context.put("metaKeywords", metaKeywords);
		context.put("metaDesc", metaDesc);

		String feedUrl = "/articles.xml";
		if (configManager.valueExists(ConfigManager.FEED_URL)) {
			feedUrl = configManager.getValue(ConfigManager.FEED_URL);
		}
		context.put("feedUrl", feedUrl);
		context.put("feedDescription",
				configManager.getValue(ConfigManager.FEED_DESCRIPTION));

		return context;
	}

	protected String generateTemplate(String name) {
		return generateTemplate(name, new VelocityContext());
	}

	protected String generateTemplate(String name, VelocityContext context) {
		StringWriter writer = new StringWriter();
		try {
			Template t = velocityEngine.getTemplate(name);
			t.merge(context, writer);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return writer.toString();
	}

	public void writeResponse(String output, HttpServletResponse resp) {
		writeResponse(output, resp, "text/html; charset=utf-8");
	}

	public void writeResponse(String output, HttpServletResponse resp,
			String contentType) {
		resp.setContentType(contentType);
		resp.setCharacterEncoding("utf-8");
		try {
			resp.getWriter().write(output);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	protected void sendErrorResponse(HttpServletResponse resp) {
		sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	protected void sendErrorResponse(HttpServletResponse resp, int errorCode) {
		resp.setStatus(errorCode);
		String output = generateTemplate("errorPage.vm");
		writeResponse(output, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
