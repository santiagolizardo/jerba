package com.santiagolizardo.jerba.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

public class CacheSingleton {

	private static final int MINUTE = 60;
	private static final int HOUR = MINUTE * 60;
	
	private static final Logger LOGGER = Logger.getLogger(CacheSingleton.class
			.getName());

	private static CacheSingleton singleton;

	public static CacheSingleton getInstance() {
		if (singleton == null)
			singleton = new CacheSingleton();

		return singleton;
	}

	private Cache cache;

	private CacheSingleton() {
		Map<String, Object> props = new HashMap<>();
		props.put(GCacheFactory.EXPIRATION_DELTA, HOUR * 8);

		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			cache = cacheFactory.createCache(props);
		} catch (CacheException e) {
			LOGGER.severe(e.getMessage());
		}
	}

	public Cache getCache() {
		return cache;
	}
}
