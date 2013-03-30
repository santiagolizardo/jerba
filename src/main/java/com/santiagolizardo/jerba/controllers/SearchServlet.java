package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.SearchManager;
import com.santiagolizardo.jerba.model.SearchResult;

import org.apache.velocity.VelocityContext;

public class SearchServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String query = req.getParameter("query");

		List<SearchResult> searchResults = SearchManager.getInstance().search(
				query);

		VelocityContext context = prepareContext(req);
		context.put("query", query);
		context.put("numResults", searchResults.size());
		context.put("results", searchResults);
		context.put("pageTitle", "Search results: " + query);
		String output = generateTemplate("search/results.vm", context);
		writeResponse(output, resp);
	}
}
