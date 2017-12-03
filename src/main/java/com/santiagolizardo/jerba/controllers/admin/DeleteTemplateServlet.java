package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Template;

public class DeleteTemplateServlet extends BaseServlet {

	private static final Logger LOGGER = Logger
			.getLogger(DeleteTemplateServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		if (null == id) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Template template = pm.getObjectById(Template.class, id);
			pm.deletePersistent(template);
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		} finally {
			pm.close();
		}

		resp.sendRedirect("/admin/template/");
	}
}
