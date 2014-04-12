package com.santiagolizardo.jerba.managers;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.santiagolizardo.jerba.model.Tag;

public class TagManager {

	private PersistenceManager pm;

	public TagManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void save(String[] tokens) {
		for (String token : tokens) {
			Tag tag = findByName(token);
			if (tag == null) {
				tag = new Tag(token);
			}
			tag.setCount(tag.getCount() + 1);
			pm.makePersistent(tag);
		}
	}

	public List<Tag> findAll(int limit) {
		Query q = pm.newQuery(Tag.class);
		q.setOrdering("count DESC");
		q.setRange(0, limit);
		return (List<Tag>) q.execute();
	}

	public Tag findByName(String name) {
		Tag tag = null;

		Query q = pm.newQuery(Tag.class);
		q.setFilter("name == nameParam");
		q.declareParameters("String nameParam");
		List<Tag> stats = (List<Tag>) q.execute(name);
		if (stats.size() == 1) {
			tag = stats.get(0);
		}
		q.closeAll();

		return tag;
	}
}
