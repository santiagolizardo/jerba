package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Template;

public class AddTemplateServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String identifier = req.getParameter("identifier");
		String content = req.getParameter("content");

		if (identifier != null && content != null) {
			Text text = new Text(content);
			Template template = new Template();
			template.setIdentifier(identifier);
			template.setContent(text);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.makePersistent(template);
			pm.close();
		}

		resp.sendRedirect("/admin/template/");
	}
}
