package com.santiagolizardo.jerba.managers;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

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

	private ArticleManager() {
	}

	public Article findByPrimaryKey(Long id) {
		Article article = null;
		Key key = KeyFactory.createKey(Article.class.getSimpleName(), id);
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			article = pm.getObjectById(Article.class, key);
			article = pm.detachCopy(article);
		} catch (JDOObjectNotFoundException e) {
			LOGGER.warning(e.getMessage());
		} finally {
			pm.close();
		}

		return article;
	}

	public List<Article> findAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(Article.class);
		List<Article> posts = (List<Article>) q.execute();
		posts.size();
		q.closeAll();

		pm.close();

		return posts;
	}

	public List<Article> findByType(ArticleType type) {
		return findByType(type, false);
	}

	public List<Article> findByType(ArticleType type, boolean includeChildren) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(Article.class);
		q.setFilter("type == :type && parent == null");
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

		pm.close();

		return posts;
	}

	public List<Article> findByParent(Key key) {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(Article.class);
		q.setFilter("parent == :parent");
		q.setOrdering("publicationDate DESC");
		List<Article> posts = (List<Article>) q.execute(key);
		posts.size();
		q.closeAll();

		pm.close();

		return posts;
	}
}
