package com.santiagolizardo.jerba.managers;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Text;
import com.santiagolizardo.jerba.model.Template;
import com.santiagolizardo.jerba.utilities.StreamUtils;
import com.santiagolizardo.jerba.utilities.templates.DatastoreResourceLoader;

public class TemplateManager {

	private static final Logger logger = Logger.getLogger(TemplateManager.class.getName());

	private PersistenceManager pm;

	public TemplateManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public Template findByPrimaryKey(String id) {
		Template template = null;

		try {
			template = pm.getObjectById(Template.class, id);
		} catch (JDOObjectNotFoundException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}

		return template;
	}

	public Template findById(String id) {
		Template tpl = null;

		Query query = pm.newQuery(Template.class);
		query.setFilter("identifier == identifierParam");
		query.declareParameters("String identifierParam");

		List<Template> results = (List<Template>) query.execute(id);
		if (results.size() == 0) {
			tpl = new Template();
			String resourceName = "defaults/" + id;
			InputStream is = DatastoreResourceLoader.class.getResourceAsStream(resourceName);
			String text = "";
			try {
				text = StreamUtils.convertStreamToString(is);
			} catch (Exception e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
			tpl.setContent(new Text(text));
		} else {
			tpl = results.get(0);
		}

		query.closeAll();

		return tpl;
	}

	public List<Template> findAll() {
		Query q = pm.newQuery(Template.class);
		return (List<Template>) q.execute();
	}
}
