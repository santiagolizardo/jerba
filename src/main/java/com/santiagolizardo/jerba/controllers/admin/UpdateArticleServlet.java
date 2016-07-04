package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.managers.ArticleManager;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.ArticleType;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.RequestParam;

public class UpdateArticleServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestParam input = new RequestParam(req);
		Long id = input.getLong("articleId");
		String type = req.getParameter("type");
		String title = req.getParameter("title");
		String sanitizedTitle = req.getParameter("sanitizedTitle");
		String keywords = req.getParameter("keywords");
		String description = req.getParameter("description");
		Text content = new Text(req.getParameter("content"));
		int order = input.getInteger("order");
		boolean visible = input.getBoolean("visible", true);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Article article = new ArticleManager(pm).findByPrimaryKey(id);
		if (null == article) {
			logger.severe("Article not found: " + id);
		} else {
			pm.currentTransaction().begin();
			article.setType(type.equals("P") ? ArticleType.Permanent
					: ArticleType.Ephemeral);
			article.setTitle(title);
			article.setSanitizedTitle(sanitizedTitle);
			article.setKeywords(keywords);
			article.setDescription(description);
			article.setContent(content);
			article.setPosition(order);
			article.setVisible(visible);
			article.setModificationDate(new Date());
			pm.makePersistent( article );
			pm.currentTransaction().commit();
		}
		pm.close();

		resp.sendRedirect("/admin/post/");
	}
}
