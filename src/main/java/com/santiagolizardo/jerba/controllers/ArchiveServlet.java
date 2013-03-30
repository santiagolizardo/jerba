package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.cache.Cache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.Archive;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

import org.apache.velocity.VelocityContext;

@SuppressWarnings("serial")
public class ArchiveServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String output = null;
		String cacheKey = "page-archive";
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			ConfigManager configManager = ConfigManager.getInstance();

			List<Article> articles = ArticleManager.getInstance().findByType(
					ArticleType.Ephemeral, false);

			Archive archive = new Archive();
			archive.setDate(01, 2011);
			archive.setArticles(articles);

			List<Archive> archives = new ArrayList<Archive>();
			archives.add(archive);

			VelocityContext context = prepareContext(req);
			context.put("archives", archives);
			context.put(
					"pageTitle",
					"Archive (articles by date) "
							+ configManager
									.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX));
			output = generateTemplate("archive.vm", context);
			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
