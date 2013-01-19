package org.jesko.picture.pusher.service;

import java.util.UUID;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;

@ContextSingleton
public class ImageNamer {

	@Inject
	private Context context;
	
	private String fileName;
	
	public String generateNextFileName() {
		fileName = context.getFilesDir() + "/" + UUID.randomUUID().toString();
		return fileName;
	}
	
	public String getCurrentFileName() {
		return fileName;
	}
}
