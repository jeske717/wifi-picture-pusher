package org.jesko.picture.pusher.service;

import java.io.File;
import java.util.UUID;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class ImageNameCreator {

	@Inject
	private Context context;
	
	@Inject
	private ImageNameModel imageNameModel;
	
	public File generateNextFile() {
		File file = new File(context.getExternalFilesDir(null), UUID.randomUUID().toString() + ".jpg");
		Log.i(getClass().getName(), "New filename for image capture: " + file.getAbsolutePath());
		imageNameModel.setCurrentFile(file);
		return imageNameModel.getCurrentFile();
	}
	
	public File getCurrentFile() {
		return imageNameModel.getCurrentFile();
	}
}
