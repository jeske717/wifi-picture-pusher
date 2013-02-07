package org.jesko.picture.pusher.service;

import java.io.File;

import com.google.inject.Singleton;

@Singleton
public class ImageNameModel {

	private File file;

	public File getCurrentFile() {
		return file;
	}
	
	public void setCurrentFile(File file) {
		this.file = file;
	}

}
