package com.santiagolizardo.jerba.utilities;

import junit.framework.TestCase;

public class CacheSingletonTestCase extends TestCase {

	public void testUniqueInstance() {
		assertSame(CacheSingleton.getInstance(), CacheSingleton.getInstance());
	}
}
