package com.santiagolizardo.jerba.managers;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Template;
import com.santiagolizardo.jerba.utilities.StreamUtils;
import com.santiagolizardo.jerba.utilities.templates.TemplateTools;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class TemplateManager {

	private static final Logger LOGGER = Logger.getLogger(TemplateManager.class
			.getName());

	private static TemplateManager singleton;

	public static TemplateManager getInstance() {
		if (null == singleton)
			singleton = new TemplateManager();
		return singleton;
	}

	public Template findByPrimaryKey(String id) {
		Template template = null;
		Key key = KeyFactory.createKey(Template.class.getSimpleName(), id);
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			template = pm.getObjectById(Template.class, key);
			//template = pm.detachCopy(template);
		} catch (JDOObjectNotFoundException e) {
			LOGGER.warning(e.getMessage());
		} finally {
			pm.close();
		}

		return template;
	}

	public Template findById(String id) {
		Template tpl = null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Template.class);
		query.setFilter("identifier == identifierParam");
		query.declareParameters("String identifierParam");

		List<Template> results = (List<Template>) query.execute(id);
		if (results.size() == 0) {
			tpl = new Template();
			String resourceName = "defaults/" + id;
			InputStream is = TemplateTools.class
					.getResourceAsStream(resourceName);
			String text = "";
			try {
				text = StreamUtils.convertStreamToString(is);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tpl.setContent(new Text(text));
		} else {
			tpl = results.get(0);
		}

		query.closeAll();
		pm.close();

		return tpl;
	}

	public List<Template> findAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(Template.class);
		List<Template> templates = (List<Template>) q.execute();
		templates.size();
		q.closeAll();

		pm.close();

		return templates;
	}
}
