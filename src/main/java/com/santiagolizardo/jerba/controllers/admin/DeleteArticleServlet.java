package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.Article;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.utilities.RequestParam;

public class DeleteArticleServlet extends BaseServlet {

	private static final Logger LOGGER = Logger
			.getLogger(DeleteArticleServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = new RequestParam(req).getLong("id");
		if (null == id) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Article article = pm.getObjectById(Article.class, id);
			pm.deletePersistent(article);
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		} finally {
			pm.close();
		}

		resp.sendRedirect("/admin/post/");
	}
}
