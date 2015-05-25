package com.sevendown.FileCache;

import org.junit.Test;

import junit.framework.TestCase;

public class CacheFileTest extends TestCase {
	@Test
	public void test_file_has_timed_out() throws InterruptedException {
		CacheFile cacheFile = new CacheFile(null, 1);
		Thread.sleep(2000);
		assertTrue(cacheFile.hasTimedOut());
	}
	@Test
	public void test_file_has_not_timed_out() throws InterruptedException {
		CacheFile cacheFile = new CacheFile(null, 10);
		assertFalse(cacheFile.hasTimedOut());
	}
	@Test
	public void test_file_without_timeout_limit_does_not_indicate_time_out() {
		CacheFile cacheFile = new CacheFile(null);
		assertFalse("The CacheFile indicated a timeout even though no limit had been set", cacheFile.hasTimedOut());
	}
}
