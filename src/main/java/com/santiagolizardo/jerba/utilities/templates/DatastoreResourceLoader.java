package com.santiagolizardo.jerba.utilities.templates;

import java.io.*;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import com.santiagolizardo.jerba.managers.TemplateManager;
import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Template;
import com.santiagolizardo.jerba.utilities.CacheSingleton;
import org.apache.velocity.util.ExtProperties;

@SuppressWarnings("unchecked")
public class DatastoreResourceLoader extends ResourceLoader {

	private static final Logger LOGGER = Logger
			.getLogger(DatastoreResourceLoader.class.getName());

	public DatastoreResourceLoader() {
	}

	@Override
	public long getLastModified(Resource resource) {
		return 0;
	}

	@Override
	public void init(ExtProperties extProperties) {

	}

	@Override
	public Reader getResourceReader(String id, String encoding) throws ResourceNotFoundException {
		byte[] contentBytes;

		Cache cache = CacheSingleton.getInstance().getCache();

		if (cache.containsKey(id)) {
			LOGGER.fine("returning cached template with id: " + id);
			contentBytes = (byte[]) cache.get(id);
		} else {
			try {
				PersistenceManager pm = PMF.get().getPersistenceManager();
				Template tpl = new TemplateManager(pm).findById(id);
				contentBytes = tpl.getContent().getValue().getBytes("UTF-8");
				pm.close();

				cache.put(id, contentBytes);
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
				contentBytes = (id + " not found").getBytes();
			}
		}

		ByteArrayInputStream is = new ByteArrayInputStream(contentBytes);
		return new InputStreamReader(is);
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		return true;
	}
}
