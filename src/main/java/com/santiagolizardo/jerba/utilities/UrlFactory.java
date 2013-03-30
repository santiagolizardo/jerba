package com.santiagolizardo.jerba.utilities;

import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.Resource;
import com.santiagolizardo.jerba.model.SearchResult;

public class UrlFactory {

	private static UrlFactory singleton = null;

	public static UrlFactory getInstance() {
		if (singleton == null) {
			singleton = new UrlFactory();
		}

		return singleton;
	}

	private WebUtils webUtils;

	private UrlFactory() {
		webUtils = WebUtils.getInstance();
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
		return String.format("/article/%s/%d", webUtils.sanitizeURL(article
				.getTitle()), article.getKey().getId());
	}

	/**
	 * 
	 * @param searchResult
	 * @return
	 */
	public String createPostUrl(SearchResult searchResult) {
		return String.format("/article/%s/%d",
				webUtils.sanitizeURL(searchResult.getTitle()),
				searchResult.getId());
	}

	public String createPageUrl(String serverName, Article page) {
		return String.format("http://%s%s", serverName, createPageUrl(page));
	}

	public String createPageUrl(Article page) {
		return String.format("/page/%s/%d",
				webUtils.sanitizeURL(page.getTitle()), page.getKey().getId());
	}

	public String createResourceUrl(Resource resource) {
		String extension = resource.getFilenameExtension();
		return String.format("/r/%s_%d%s", webUtils.sanitizeURL(resource
				.getTitle()), resource.getKey().getId(), extension == null ? ""
				: "." + extension);
	}
}
