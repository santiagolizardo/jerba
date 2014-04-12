package com.santiagolizardo.jerba.managers;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.Resource;

public class ResourceManager {

	private PersistenceManager pm;

	public ResourceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public Resource findById(Long id) {
		Resource resource = pm.getObjectById(Resource.class, id);
		return resource;
	}

	public List<Resource> findAll() {
		Query q = pm.newQuery(Resource.class);
		return (List<Resource>) q.execute();
	}
}
