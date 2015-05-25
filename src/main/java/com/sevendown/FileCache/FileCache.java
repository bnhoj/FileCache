package com.sevendown.FileCache;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;

public class FileCache {
	ConcurrentHashMap<String,CacheFile> filePaths;
	public FileCache() {
		filePaths = new ConcurrentHashMap<String,CacheFile>();
	}
	public byte[] retrieveFile(String filePath) throws IOException {
		if(fileIsInCache(filePath))
			return getFileFromMemory(filePath);
		else
			return storeFileInMemory(filePath);
	}
	
	public void removeFileFromCache(String filePath) {
		filePaths.remove(filePath);
	}
	public byte[] upsertIntoCache(String filePath) throws IOException {
		return storeFileInMemory(filePath);
	}
	public byte[] upsertIntoCache(String filePath, int cacheTimeInSeconds) throws IOException {
		return storeFileInMemory(filePath, cacheTimeInSeconds);
	}
	public boolean fileIsInCache(String filePath) {
		return filePaths.containsKey(filePath) && !filePaths.get(filePath).hasTimedOut();
	}
	private byte[] storeFileInMemory(String filePath) throws IOException {
		return storeFileInMemory(filePath, -1);
	}
	private byte[] storeFileInMemory(String filePath, int cacheTimeInSeconds) throws IOException {
		CacheFile file;
		if(cacheTimeInSeconds > 0) {
			file = new CacheFile(FileUtils.readFileToByteArray(new File(filePath)), cacheTimeInSeconds);
		} else {
			file = new CacheFile(FileUtils.readFileToByteArray(new File(filePath)));
		}
		filePaths.put(filePath,file);
		return getFileFromMemory(filePath);
	}
	private byte[] getFileFromMemory(String filePath) {
		return filePaths.get(filePath).file().clone();
	}
}
