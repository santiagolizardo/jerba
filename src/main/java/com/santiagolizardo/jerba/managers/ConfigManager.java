package com.santiagolizardo.jerba.managers;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.ConfigValue;
import com.santiagolizardo.jerba.model.PMF;

@SuppressWarnings("unchecked")
public class ConfigManager {

	private static final Logger LOGGER = Logger.getLogger(ConfigManager.class
			.getName());

	public static final String WEBSITE_NAME = "website.name";
	public static final String WEBSITE_SLOGAN = "website.slogan";
	public static final String WEBSITE_URL = "website.url";
	public static final String WEBSITE_TITLE_SUFFIX = "website.title.suffix";

	public static final String META_TITLE = "meta.title";
	public static final String META_KEYWORDS = "meta.keywords";
	public static final String META_DESCRIPTION = "meta.description";

	public static final String FEED_DESCRIPTION = "feed.description";
	public static final String FEED_URL = "feed.url";

	public static final String RECAPTCHA_PUBLIC_KEY = "recaptcha.public.key";
	public static final String RECAPTCHA_PRIVATE_KEY = "recaptcha.private.key";
	public static final String ADMINISTRATOR_EMAIL = "administrator.email";
	public static final String ADMINISTRATOR_NAME = "administrator.name";

	public static final String[] NAMES = { WEBSITE_NAME, WEBSITE_SLOGAN,
			WEBSITE_URL, WEBSITE_TITLE_SUFFIX, META_TITLE, META_KEYWORDS,
			META_DESCRIPTION, FEED_DESCRIPTION, FEED_URL, RECAPTCHA_PUBLIC_KEY,
			RECAPTCHA_PRIVATE_KEY, ADMINISTRATOR_EMAIL, ADMINISTRATOR_NAME, };

	private static ConfigManager configManager;

	public static ConfigManager getInstance() {
		if (configManager == null)
			configManager = new ConfigManager();
		return configManager;
	}

	private PersistenceManager pm;

	public ConfigManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public ConfigManager() {
		this(PMF.get().getPersistenceManager());
	}

	public ConfigValue findByName(String name) {
		Query query = pm.newQuery(ConfigValue.class);
		query.setFilter("name == nameParam");
		query.declareParameters("String nameParam");

		List<ConfigValue> results = (List<ConfigValue>) query.execute(name);
		results.size();
		query.closeAll();

		if (results.size() == 0) {
			LOGGER.warning("Config value not found for: " + name);
			return new ConfigValue(name, name + " (undefined)");
		} else
			return results.get(0);
	}

	public boolean valueExists(String name) {
		Query query = pm.newQuery(ConfigValue.class);
		query.setFilter("name == nameParam");
		query.declareParameters("String nameParam");

		List<ConfigValue> results = (List<ConfigValue>) query.execute(name);
		boolean exists = ( results.size() > 0 );
		query.closeAll();

		return exists;
	}

	public String getValue(String name) {
		return findByName(name).getValue();
	}

	public List<ConfigValue> findAll() {
		Query query = pm.newQuery(ConfigValue.class);
		List<ConfigValue> results = (List<ConfigValue>) query.execute();
		results.size();
		query.closeAll();
		return results;
	}
}
