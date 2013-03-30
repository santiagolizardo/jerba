package com.santiagolizardo.jerba.managers;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.PMF;
import com.santiagolizardo.jerba.model.Resource;

public class ResourceManager {

	public static Resource findById(Long id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Resource resource = pm.getObjectById(Resource.class, id);
		pm.close();

		return resource;
	}

	public static List<Resource> findAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query q = pm.newQuery(Resource.class);
		List<Resource> resources = (List<Resource>) q.execute();
		resources.size();
		q.closeAll();

		pm.close();

		return resources;
	}
}
