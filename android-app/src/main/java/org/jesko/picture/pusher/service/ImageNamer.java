package org.jesko.picture.pusher.service;

import java.io.File;
import java.util.UUID;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class ImageNamer {

	@Inject
	private Context context;
	
	private File file;
	
	public File generateNextFile() {
		file = new File(context.getExternalFilesDir(null), UUID.randomUUID().toString() + ".jpg");
		Log.i(getClass().getName(), "New filename for image capture: " + file.getAbsolutePath());
		return file;
	}
	
	public File getCurrentFile() {
		return file;
	}
}
