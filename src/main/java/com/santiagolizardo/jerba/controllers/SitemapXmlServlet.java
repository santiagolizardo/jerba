package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.CacheSingleton;
import com.santiagolizardo.jerba.utilities.UrlFactory;

@SuppressWarnings("unchecked")
public class SitemapXmlServlet extends BaseServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Cache cache = CacheSingleton.getInstance().getCache();

		String output = null;
		String cacheKey = "feed-sitemap";
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			UrlFactory urlFactory = UrlFactory.getInstance();

			Writer writer = new StringWriter();
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			writer.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
			writer.write("<url>");
			writer.write("<loc>http://" + req.getServerName() + "</loc>");
			writer.write("<changefreq>daily</changefreq>");
			writer.write("<priority>1.0</priority>");
			writer.write("</url>");

			PersistenceManager pm = PMF.get().getPersistenceManager();
			ArticleManager articleManager = new ArticleManager(pm);
			List<Article> pages = articleManager.findByType(ArticleType.Permanent, false, 100);
			for (Article page : pages) {
				writer.write("<url>");
				writer.write("<loc>" + urlFactory.createPageUrl(req.getServerName(), page) + "</loc>");
				writer.write("<changefreq>weekly</changefreq>");
				final Date modDate = page.getModificationDate();
				if (modDate != null) {
					writer.write("<lastmod>" + formatW3cDate(page.getModificationDate()) + "</lastmod>");
				}
				writer.write("<priority>0.9</priority>");
				writer.write("</url>");
			}
			List<Article> articles = articleManager.findByType(ArticleType.Ephemeral, false, 100);
			for (Article article : articles) {
				writer.write("<url>");
				writer.write("<loc>" + urlFactory.createPostUrl(req.getServerName(), article) + "</loc>");
				writer.write("<changefreq>weekly</changefreq>");
				final Date modDate = article.getModificationDate();
				if (modDate != null) {
					writer.write("<lastmod>" + formatW3cDate(article.getModificationDate()) + "</lastmod>");
				}
				writer.write("<priority>0.5</priority>");
				writer.write("</url>");
			}

			writer.write("<url>");
			writer.write("<loc>http://" + req.getServerName() + "/contact</loc>");
			writer.write("<changefreq>monthly</changefreq>");
			writer.write("<priority>0.5</priority>");
			writer.write("</url>");

			writer.write("</urlset>");
			output = writer.toString();
			cache.put(cacheKey, output);
			pm.close();
		}

		writeResponse(output, resp, "text/xml; charset=utf-8");
	}

	private String formatW3cDate(final Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
		return format.format(date).replaceAll("(.*)(\\d\\d)$", "$1:$2");
	}
}
