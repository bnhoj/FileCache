package com.sevendown.FileCache;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
public class FileCacheTest extends TestCase {
	String pathToTestFile = Thread.currentThread().getContextClassLoader().getResource("helloworld.txt").getPath();
	FileCache cache;
	
	@Before
	public void setUp() {
		cache = new FileCache();
	}
	
	@Test
	public void testpull_file_from_disk() throws IOException {
		byte[] testFile = readTestFile();
		assertTrue("The correct file was not read from disk",Arrays.equals(testFile, cache.retrieveFile(pathToTestFile)));
	}

	
	@Test
	public void testpull_file_from_disk_and_then_modify_it_and_check_that_the_cache_hasnt_changed() throws IOException {
		byte[] firstCachePull = cache.retrieveFile(pathToTestFile);
		
		FileUtils.moveFile(new File(pathToTestFile), new File(pathToTestFile + "tmp"));
		try {
			assertTrue("The modified file was read from disk. The cached version in memory should've been used.",Arrays.equals(firstCachePull, cache.retrieveFile(pathToTestFile)));
		} finally {
			FileUtils.moveFile(new File(pathToTestFile + "tmp"), new File(pathToTestFile));
		}
	}
	
	@Test
	public void testpull_file_from_cache_modify_the_instance_and_make_sure_that_the_cache_hasnt_changed() throws IOException {
		byte[] testFile = readTestFile();
		byte[] firstInMemoryFile = cache.retrieveFile(pathToTestFile);
		
		setArrayToAllZeroes(firstInMemoryFile);
		
		assertTrue("The cached file has changed after manipulating one of the instances fetched from the cache",Arrays.equals(testFile, cache.retrieveFile(pathToTestFile)));
	}

	private void setArrayToAllZeroes(byte[] firstInMemoryFile) {
		for(int i = 0; i < firstInMemoryFile.length; i++) {
			firstInMemoryFile[i] = 0;
		}
	}
	
	private byte[] readTestFile() throws IOException {
		return FileUtils.readFileToByteArray(new File(pathToTestFile));
	}
}
