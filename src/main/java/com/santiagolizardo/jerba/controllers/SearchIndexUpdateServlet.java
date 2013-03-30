package com.santiagolizardo.jerba.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.managers.SearchManager;
import com.santiagolizardo.jerba.model.Article;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;

public class SearchIndexUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Article> articles = ArticleManager.getInstance().findAll();
		Index index = SearchManager.getInstance().getIndex();
		for (Article article : articles) {
			Document doc = SearchManager.getInstance().createDocument(article);
			index.put(doc);
		}
	}
}
