package com.sevendown.FileCache;

import java.util.Date;

public class CacheFile {
	private byte[] file;
	private Date timeOutLimit;
	
	public CacheFile(byte[] file) {
		this.timeOutLimit = null;
		this.file = file;
	}
	
	public CacheFile(byte[] file, int cacheTimeInSeconds) {
		this.timeOutLimit = new Date(new Date().getTime() + cacheTimeInSeconds * 1000);
		this.file = file;
	}
	
	public byte[] file() {
		return file;
	}
	
	public boolean hasTimedOut() {
		if(timeOutLimit == null) {
			return false;
		}
		Date now = new Date();
		return now.after(timeOutLimit); 
	}
}
