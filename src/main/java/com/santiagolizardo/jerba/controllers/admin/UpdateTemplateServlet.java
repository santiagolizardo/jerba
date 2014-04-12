package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;

import com.santiagolizardo.jerba.managers.TemplateManager;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Template;

import com.google.appengine.api.datastore.Text;

/**
 * Updates a template in the datastore.
 */
public class UpdateTemplateServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String identifier = req.getParameter("identifier");
		String content = req.getParameter("content");

		if (identifier != null && content != null) {
			Text contentText = new Text(content);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Template template = new TemplateManager( pm ).findById( identifier );
			template.setContent(contentText);
			pm.close();
		}

		resp.sendRedirect("/admin/template/");
	}
}
