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

public class EditConfigServlet extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String type = req.getParameter("type");
		String value = req.getParameter("value");

		if (name != null && type != null && value != null) {			
			PersistenceManager pm = PMF.get().getPersistenceManager();
			ConfigManager configManager = new ConfigManager(pm);
			ConfigValue configValue = configManager.findByName(name);
			configValue.setType(type);
			configValue.setValue(value);
			pm.makePersistent(configValue);
			pm.close();
		}

		resp.sendRedirect("/admin/config/");
	}
}
