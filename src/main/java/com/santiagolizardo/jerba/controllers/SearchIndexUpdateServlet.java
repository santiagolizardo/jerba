package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.SearchManager;
import com.santiagolizardo.jerba.model.Article;

public class SearchIndexUpdateServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		SearchManager searchManager = SearchManager.getInstance();

		List<Article> articles = ArticleManager.getInstance().findAll();
		Index index = searchManager.getIndex();
		for (Article article : articles) {
			Document doc = searchManager.createDocument(article);
			index.put(doc);
		}
	}
}
