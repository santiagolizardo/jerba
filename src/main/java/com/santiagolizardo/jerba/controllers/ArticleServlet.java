package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

public class ArticleServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] tokens = req.getRequestURI().split("/");
		if (tokens.length < 3) {
			sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		String sanitizedTitle = tokens[2];

		String cacheKey = "page-article-" + sanitizedTitle;
		String output = null;
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			ArticleManager articleManager = ArticleManager.getInstance();
			Article post = articleManager.findBySanitizedTitle(sanitizedTitle);
			if (null == post) {
				sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			post.setChildren(articleManager.findByParent(post.getKey()));

			ConfigManager configManager = ConfigManager.getInstance();

			VelocityContext context = prepareContext(req);
			context.put("article", post);
			context.put(
					"pageTitle",
					post.getTitle()
							+ configManager
									.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX));
			context.put("metaKeywords", post.getKeywords());
			context.put("metaDesc", post.getDescription());

			output = generateTemplate(
					post.getType() == ArticleType.Ephemeral ? "article.vm"
							: "page.vm", context);

			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
