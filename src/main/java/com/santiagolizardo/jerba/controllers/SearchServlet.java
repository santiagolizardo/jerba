package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import com.santiagolizardo.jerba.managers.SearchManager;
import com.santiagolizardo.jerba.model.SearchResult;
import com.santiagolizardo.jerba.utilities.CacheSingleton;
import com.santiagolizardo.jerba.utilities.StringUtils;

public class SearchServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String query = req.getParameter("query");

		String cacheKey = "search-results-" + query;
		String output = null;
		Cache cache = CacheSingleton.getInstance().getCache();
		if (cache.containsKey(cacheKey)) {
			output = (String) cache.get(cacheKey);
		} else {
			List<SearchResult> searchResults = SearchManager.getInstance().search(query);

			VelocityContext context = prepareContext(req);
			context.put("query", query);
			context.put("numResults", searchResults.size());
			context.put("results", searchResults);
			context.put("pageTitle", String.format("%s articles", StringUtils.capitalizeFirst(query)));
			output = generateTemplate("search/results.vm", context);
			cache.put(cacheKey, output);
		}
		
		writeResponse(output, resp);
	}
}
