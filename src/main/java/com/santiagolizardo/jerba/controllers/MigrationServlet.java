package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.TagManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.StringUtils;

@SuppressWarnings("serial")
public class MigrationServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		ArticleManager articleManager = new ArticleManager(pm);
		TagManager tagManager = new TagManager(pm);

		List<Article> articles = articleManager.findAll();
		for (Article article : articles) {
			String sanitizedTitle = StringUtils.sanitize(article.getTitle());
			article.setSanitizedTitle(sanitizedTitle);
			pm.makePersistent(article);

			/*
			String[] tokens = StringUtils.tokenize(article.getKeywords());
			tagManager.save(tokens);

			 * int year = article.getPublicationDate().getYear() + 1900; int
			 * month = article.getPublicationDate().getMonth() + 1;
			 * 
			 * ArchiveStats archiveStats = ArchiveStatsManager.getInstance()
			 * .findByYearMonth(year, month); if (archiveStats == null) {
			 * archiveStats = new ArchiveStats(year, month); }
			 * archiveStats.setCount(archiveStats.getCount() + 1);
			 * PMF.save(archiveStats);
			 */

		}

		pm.close();
	}
}
