package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.cache.Cache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

import org.apache.velocity.VelocityContext;

@SuppressWarnings("serial")
public class IndexServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String output = null;

		String cacheKey = "page-home";
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			int articlesPerPage = 6;
			List<Article> articles = ArticleManager.getInstance().findByType(
					ArticleType.Ephemeral);
			if (articles.size() > articlesPerPage)
				articles = articles.subList(0, articlesPerPage);

			VelocityContext context = prepareContext(req);
			context.put("articles", articles);
			output = generateTemplate("home.vm", context);
			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
