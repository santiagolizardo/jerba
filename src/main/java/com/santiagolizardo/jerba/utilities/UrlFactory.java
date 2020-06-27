package com.santiagolizardo.jerba.utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Logger;

import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.Resource;
import com.santiagolizardo.jerba.model.SearchResult;

public class UrlFactory {

	private static final Logger logger = Logger.getLogger(UrlFactory.class.getName());

	private static UrlFactory singleton = null;

	public static UrlFactory getInstance() {
		if (singleton == null) {
			singleton = new UrlFactory();
		}

		return singleton;
	}

	private UrlFactory() {
	}

	public String createSearchUrl(String query) {
		try {
			return String.format("/search?query=%s",
					URLEncoder.encode(query, "UTF-8"));

		} catch (UnsupportedEncodingException uee) {
			logger.warning(uee.getMessage());
			return String.format("/search?query=%s", query);
		}
	}

	/**
	 * 
	 * @param serverName
	 * @param article
	 * @return
	 */
	public String createPostUrl(String serverName, Article article) {
		return String.format("http://%s%s", serverName, createPostUrl(article));
	}

	/**
	 * 
	 * @param article
	 * @return
	 */
	public String createPostUrl(Article article) {
		return String.format("/article/%s", article.getSanitizedTitle());
	}

	/**
	 * 
	 * @param searchResult
	 * @return
	 */
	public String createPostUrl(SearchResult searchResult) {
		return String.format("/article/%s", searchResult.getSanitizedTitle());
	}

	public String createPageUrl(String serverName, Article page) {
		return String.format("http://%s%s", serverName, createPageUrl(page));
	}

	public String createPageUrl(Article page) {
		return String.format("/page/%s", page.getSanitizedTitle());
	}

	public String createResourceUrl(Resource resource) {
		String extension = resource.getFilenameExtension();
		return String.format("/r/%s_%d%s", StringUtils.sanitize(resource
				.getTitle()), resource.getKey().getId(), extension == null ? ""
				: "." + extension);
	}
}
