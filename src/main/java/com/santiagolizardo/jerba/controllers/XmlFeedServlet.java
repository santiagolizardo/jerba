package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.CacheSingleton;
import com.santiagolizardo.jerba.utilities.UrlFactory;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class XmlFeedServlet extends BaseServlet {

	private static final Logger logger = Logger.getLogger(XmlFeedServlet.class
			.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String output = null;
		String cacheKey = "feed-articles";
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			PersistenceManager pm = null;
			try {
				SyndFeed feed = new SyndFeedImpl();
				feed.setFeedType("rss_2.0");

				pm = PMF.get().getPersistenceManager();
				
				ConfigManager configManager = new ConfigManager(pm);
				feed.setTitle(configManager.getValue(ConfigManager.META_TITLE)
						+ configManager.findByName("website.title.suffix")
								.getValue());
				feed.setLink(configManager.findByName("website.url").getValue());
				feed.setDescription(configManager
						.getValue(ConfigManager.FEED_DESCRIPTION));

				List<SyndEntry> entries = new ArrayList<>();

				List<Article> articles = new ArticleManager(pm)
						.findByType(ArticleType.Ephemeral);
				for (Article article : articles) {
					SyndEntry entry;
					entry = new SyndEntryImpl();
					entry.setTitle(article.getTitle());
					entry.setLink(UrlFactory.getInstance().createPostUrl(
							req.getServerName(), article));
					entry.setPublishedDate(article.getPublicationDate());
					SyndContent description;
					description = new SyndContentImpl();
					description.setType("text/html");
					description.setValue(article.getContent().getValue());
					entry.setDescription(description);
					entries.add(entry);
				}

				feed.setEntries(entries);

				SyndFeedOutput feedOutput = new SyndFeedOutput();
				output = feedOutput.outputString(feed);

				cache.put(cacheKey, output);
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			} finally {
				if(pm != null) pm.close();
			}
		}

		writeResponse(output, resp, "text/xml; charset=utf-8");
	}
}
