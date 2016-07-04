package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.TagManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Tag;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

public class IndexServlet extends BaseServlet {

	private static final int NUM_ARTICLES_INDEX = 6;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String output = null;

		String cacheKey = "page-home";
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			List<Article> articles = new ArticleManager(pm).findByType(
					ArticleType.Ephemeral, false, NUM_ARTICLES_INDEX);

			List<Tag> tags = new TagManager(pm).findAll(20);
			pm.close();

			VelocityContext context = prepareContext(req);
			context.put("articles", articles);
			context.put("tags", tags);

			output = generateTemplate("home.vm", context);
			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
