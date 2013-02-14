package org.jesko.picture.pusher.external;

import java.io.File;

import org.jesko.picture.pusher.service.PictureSuckerServiceModel;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.inject.Inject;

public class PhotoReceiver extends RoboBroadcastReceiver {

	@Inject
	private PictureSuckerServiceModel serviceModel;
	@Inject
	private MediaStoreHelper mediaStoreHelper;
	
	@Override
	protected void handleReceive(Context context, Intent intent) {
		Uri contentUri = intent.getData();
		final String filePath = mediaStoreHelper.getFilePathFromUri(contentUri);
		Log.i(getClass().getName(), "Got file from native camera app: " + filePath);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				serviceModel.startUpload(new File(filePath));
			}
			
		}).start();
	}
}
