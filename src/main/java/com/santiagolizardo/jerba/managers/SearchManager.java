package com.santiagolizardo.jerba.managers;

import java.util.ArrayList;
import java.util.List;

import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.SearchResult;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;

public class SearchManager {

	private static final String INDEX_NAME = "article-index";

	private static SearchManager singleton;

	/**
	 * 
	 * @return
	 */
	public static final SearchManager getInstance() {
		if (null == singleton) {
			singleton = new SearchManager();
		}
		return singleton;
	}

	private SearchManager() {

	}

	/**
	 * 
	 * @return
	 */
	public Index getIndex() {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(INDEX_NAME)
				.build();
		return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}

	/**
	 * s
	 * 
	 * @param query
	 * @return
	 */
	public List<SearchResult> search(String query) {
		Results<ScoredDocument> results = getIndex().search(query);
		List<SearchResult> searchResults = prepareSearchResults(results);
		return searchResults;
	}

	/**
	 * 
	 * @param docs
	 * @return
	 */
	protected List<SearchResult> prepareSearchResults(
			Results<ScoredDocument> docs) {
		List<SearchResult> results = new ArrayList<SearchResult>();
		for (ScoredDocument doc : docs) {
			SearchResult result = new SearchResult();
			result.setId(doc.getOnlyField("id").getNumber().longValue());
			result.setTitle(doc.getOnlyField("title").getText());
			result.setDescription(doc.getOnlyField("description").getText());
			result.setKeywords(doc.getOnlyField("keywords").getText());
			result.setContent(doc.getOnlyField("content").getHTML());
			result.setPublicationDate(doc.getOnlyField("publicationDate")
					.getDate());
			results.add(result);
		}
		return results;
	}

	/**
	 * Creates a search document based on the article given as a parameter.
	 * 
	 * @param article
	 * @return
	 */
	public Document createDocument(Article article) {
		Document doc = Document
				.newBuilder()
				.setId(article.getKey().toString())
				.addField(
						Field.newBuilder().setName("id")
								.setNumber(article.getKey().getId()))
				.addField(
						Field.newBuilder().setName("title")
								.setText(article.getTitle()))
				.addField(
						Field.newBuilder().setName("description")
								.setText(article.getDescription()))
				.addField(
						Field.newBuilder().setName("keywords")
								.setText(article.getKeywords()))
				.addField(
						Field.newBuilder().setName("content")
								.setHTML(article.getContent().getValue()))
				.addField(
						Field.newBuilder().setName("publicationDate")
								.setDate(article.getPublicationDate())).build();
		return doc;
	}
}
