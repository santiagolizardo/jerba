package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.utilities.UrlFactory;
import com.santiagolizardo.jerba.utilities.WebUtils;
import com.santiagolizardo.jerba.utilities.templates.DatastoreResourceLoader;
import com.santiagolizardo.jerba.utilities.templates.TemplateTools;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;

public abstract class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 2570210304300019515L;

	private static final Logger LOGGER = Logger.getLogger(BaseServlet.class
			.getName());

	private VelocityEngine ve;

	@Override
	public void init() throws ServletException {
		super.init();

		DatastoreResourceLoader datastoreResourceLoader = new DatastoreResourceLoader();

		ve = new VelocityEngine();
//		ve.setProperty("runtime.log.logsystem.class",
//				"org.apache.velocity.runtime.log.NullLogChute");
		ve.setProperty("resource.loader", "datastore");
		ve.setProperty("datastore.resource.loader.instance",
				datastoreResourceLoader);

		try {
			ve.init();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
	}

	protected final void invokeAction(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action == null)
			action = "Default";

		try {
			Method method = getClass().getMethod("do" + action,
					HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, req, resp);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			resp.sendError(500);
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
		context.put("tools", new TemplateTools());
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
			Template t = ve.getTemplate(name);
			t.merge(context, writer);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
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
			LOGGER.severe(e.getMessage());
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
}
