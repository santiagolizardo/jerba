package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.ArchiveStatsManager;
import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.ArchiveStats;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.CacheSingleton;

@SuppressWarnings("unchecked")
public class ArchiveServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String output = null;
		String cacheKey = "page-archive";
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			PersistenceManager pm = PMF.get().getPersistenceManager();

			ConfigManager configManager = new ConfigManager(pm);
			String websiteTitleSuffix = configManager
					.getValue(ConfigManager.WEBSITE_TITLE_SUFFIX);

			ArticleManager articleManager = new ArticleManager(pm);

			List<ArchiveStats> archiveStats = new ArchiveStatsManager(pm)
					.findAll();
			for (ArchiveStats stats : archiveStats) {
				List<Article> articles = articleManager.findByYearMonth(
						stats.getYear(), stats.getMonth());
				stats.getArticles().addAll(articles);
			}
			
			pm.close();

			VelocityContext context = prepareContext(req);
			context.put("archiveStats", archiveStats);
			context.put("pageTitle", "Archive (articles by date) "
					+ websiteTitleSuffix);
			output = generateTemplate("archive.vm", context);
			cache.put(cacheKey, output);
		}

		writeResponse(output, resp);
	}
}
