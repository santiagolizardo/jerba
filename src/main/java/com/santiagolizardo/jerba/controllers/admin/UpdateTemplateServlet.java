package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;

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
 * 
 * @author slizardo
 * 
 */
public class UpdateTemplateServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		String newId = req.getParameter("newId");
		String contentParam = req.getParameter("content");

		if (id != null && contentParam != null) {
			Text content = new Text(contentParam);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Template template = pm.getObjectById(Template.class, id);
			// If the identifier is different, a new template have to be created
			// and the previous one deleted.
			if (!id.equals(newId)) {
				pm.deletePersistent(template);
				Template newTemplate = new Template();
				newTemplate.setIdentifier(newId);
				newTemplate.setContent(content);
				pm.makePersistent(newTemplate);
			} else {
				template.setContent(content);
				pm.makePersistent(template);
			}
			pm.close();
		}

		resp.sendRedirect("/admin/template/");
	}
}
