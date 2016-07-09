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

public class AddConfigServlet extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String type = req.getParameter("type");
		String value = req.getParameter("value");

		if (name != null && type != null && value != null) {
			ConfigValue configValue = new ConfigValue();
			configValue.setName(name);
			configValue.setType(type);
			configValue.setValue(value);
			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			pm.makePersistent(configValue);
			pm.close();
		}

		resp.sendRedirect("/admin/config/");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");

		if (name != null) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			ConfigValue value = new ConfigManager(pm).findByName(name);
			pm.deletePersistent(value);
			pm.close();
		}

		resp.sendRedirect("/admin/config/");
	}
}
