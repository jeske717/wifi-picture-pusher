package org.jesko.picture.pusher.service;

import java.io.File;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class ImageNameCreator {

	private final Context context;
	private final ImageNameModel imageNameModel;
	private final UuidGenerator uuidGenerator;
	
	@Inject
	public ImageNameCreator(Context context, ImageNameModel imageNameModel, UuidGenerator uuidGenerator) {
		this.context = context;
		this.imageNameModel = imageNameModel;
		this.uuidGenerator = uuidGenerator;
	}
	
	public File generateNextFile() {
		File file = new File(context.getExternalFilesDir(null), uuidGenerator.generate() + ".jpg");
		Log.i(getClass().getName(), "New filename for image capture: " + file.getAbsolutePath());
		imageNameModel.setCurrentFile(file);
		return file;
	}
	
	public File getCurrentFile() {
		return imageNameModel.getCurrentFile();
	}
}
