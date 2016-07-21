package com.santiagolizardo.jerba.controllers;

import java.io.IOException;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

public class ArticleServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String[] tokens = req.getRequestURI().split("/");
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
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			ArticleManager articleManager = new ArticleManager(pm);
			Article post = articleManager.findBySanitizedTitle(sanitizedTitle);
			if (null == post) {
				pm.close();
				sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			post.setChildren(articleManager.findByParent(post.getKey()));

			ConfigManager configManager = new ConfigManager(pm);

			VelocityContext context = prepareContext(req);
			context.put("article", post);
			context.put(
					"pageTitle",
					post.getTitle()
							+ configManager
									.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX));
			context.put("metaKeywords", post.getKeywords());
			context.put("metaDesc", post.getDescription());

			String keywordsString = post.getKeywords();
			if(keywordsString != null) {
				final String[] keywords = keywordsString.split(",");
				context.put("keywords", keywords);
			}

			output = generateTemplate(
					post.getType() == ArticleType.Ephemeral ? "article.vm"
							: "page.vm", context);

			pm.close();

			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
