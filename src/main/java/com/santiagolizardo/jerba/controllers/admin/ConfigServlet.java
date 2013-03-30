package com.santiagolizardo.jerba.controllers.admin;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.santiagolizardo.jerba.controllers.BaseServlet;
import com.santiagolizardo.jerba.managers.ConfigManager;
import com.santiagolizardo.jerba.model.ConfigValue;
import com.santiagolizardo.jerba.model.PMF;

public class ConfigServlet extends BaseServlet {

	private static final long serialVersionUID = 6465741042405945951L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String type = req.getParameter("type");
		String value = req.getParameter("value");

		if (name != null && type != null && value != null) {
			ConfigValue post = new ConfigValue();
			post.setName(name);
			post.setType(type);
			post.setValue(value);
			PMF.save(post);
		}

		resp.sendRedirect("/admin/config/");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");

		if (name != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			ConfigValue value = ConfigManager.getInstance()
					.findByName(pm, name);
			pm.deletePersistent(value);
		}

		resp.sendRedirect("/admin/config/");
	}
}
