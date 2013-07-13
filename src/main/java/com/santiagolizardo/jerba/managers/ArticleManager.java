package com.santiagolizardo.jerba.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;

public class ArticleManager {

	private static final Logger LOGGER = Logger.getLogger(ArticleManager.class
			.getName());

	private static ArticleManager singleton;

	public static ArticleManager getInstance() {
		if (null == singleton) {
			singleton = new ArticleManager();
		}
		return singleton;
	}

	private PersistenceManager pm;

	public ArticleManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public ArticleManager() {
		this(PMF.get().getPersistenceManager());
	}

	public Article findBySanitizedTitle(String sanitizedTitle) {
		Article article = null;

		try {
			Query query = pm.newQuery(Article.class);
			query.setFilter("sanitizedTitle == sanitizedTitleParam");
			query.declareParameters("String sanitizedTitleParam");
			List<Article> articles = (List<Article>) query
					.execute(sanitizedTitle);
			if (articles.size() == 1) {
				article = articles.get(0);
			}
			// article = pm.detachCopy(article);
		} catch (JDOObjectNotFoundException e) {
			LOGGER.warning(e.getMessage());
		}

		return article;
	}

	public List<Article> findByYearMonth(int year, int month) {
		List<Article> articles = new ArrayList<Article>();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date fromDate = calendar.getTime();

		calendar.add(Calendar.MONTH, 1);
		Date toDate = calendar.getTime();

		try {
			Query query = pm.newQuery(Article.class);
			query.setFilter("publicationDate >= fromDateParam && publicationDate < toDateParam");
			query.declareImports("import java.util.Date");
			query.declareParameters("Date fromDateParam, Date toDateParam");
			articles = (List<Article>) query.execute(fromDate, toDate);
			// article = pm.detachCopy(article);
		} catch (JDOObjectNotFoundException e) {
			LOGGER.warning(e.getMessage());
		}

		return articles;
	}

	public Article findByPrimaryKey(Long id) {
		Article article = null;
		Key key = KeyFactory.createKey(Article.class.getSimpleName(), id);

		try {
			article = pm.getObjectById(Article.class, key);
			article = pm.detachCopy(article);
		} catch (JDOObjectNotFoundException e) {
			LOGGER.warning(e.getMessage());
		}

		return article;
	}

	public List<Article> findAll() {
		Query q = pm.newQuery(Article.class);
		List<Article> posts = (List<Article>) q.execute();
		posts.size();
		q.closeAll();

		return posts;
	}

	public List<Article> findByType(ArticleType type) {
		return findByType(type, false, 10);
	}

	public List<Article> findByType(ArticleType type, boolean includeChildren) {
		return findByType(type, includeChildren, 10);
	}

	public List<Article> findByType(ArticleType type, boolean includeChildren,
			int limit) {
		Query q = pm.newQuery(Article.class);
		q.setFilter("type == :type && parent == null");
		q.setRange(0, limit);
		q.setOrdering(type == ArticleType.Permanent ? "position ASC"
				: "publicationDate DESC");
		List<Article> posts = (List<Article>) q.execute(type);
		if (includeChildren) {
			for (Article a : posts) {
				List<Article> children = findByParent(a.getKey());
				a.getChildren().addAll(children);
			}
		}
		posts.size();
		q.closeAll();

		return posts;
	}

	public List<Article> findByParent(Key key) {
		Query q = pm.newQuery(Article.class);
		q.setFilter("parent == :parent");
		q.setOrdering("publicationDate DESC");
		List<Article> posts = (List<Article>) q.execute(key);
		posts.size();
		q.closeAll();

		return posts;
	}
}
