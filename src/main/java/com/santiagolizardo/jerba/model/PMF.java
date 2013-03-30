package com.santiagolizardo.jerba.model;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class PMF {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

	public static void save(Object post) {
		PersistenceManager pm = get().getPersistenceManager();
		pm.currentTransaction().begin();
		pm.makePersistent(post);
		pm.currentTransaction().commit();
		pm.close();
	}
}
