package com.santiagolizardo.jerba.managers;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.santiagolizardo.jerba.model.Template;
import com.santiagolizardo.jerba.utilities.StreamUtils;
import com.santiagolizardo.jerba.utilities.templates.DatastoreResourceLoader;

public class TemplateManager {

	private static final Logger LOGGER = Logger.getLogger(TemplateManager.class
			.getName());

	private PersistenceManager pm;

	public TemplateManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public Template findByPrimaryKey(String id) {
		Template template = null;
		Key key = KeyFactory.createKey(Template.class.getSimpleName(), id);

		try {
			template = pm.getObjectById(Template.class, key);
			// template = pm.detachCopy(template);
		} catch (JDOObjectNotFoundException e) {
			LOGGER.warning(e.getMessage());
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
			InputStream is = DatastoreResourceLoader.class
					.getResourceAsStream(resourceName);
			String text = "";
			try {
				text = StreamUtils.convertStreamToString(is);
			} catch (Exception e) {
				LOGGER.warning(e.getMessage());
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
		List<Template> templates = (List<Template>) q.execute();
		templates.size();
		q.closeAll();

		return templates;
	}
}
