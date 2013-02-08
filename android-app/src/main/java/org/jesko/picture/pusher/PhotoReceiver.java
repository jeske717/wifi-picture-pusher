package org.jesko.picture.pusher;

import java.io.File;

import org.jesko.picture.pusher.service.PictureSuckerServiceModel;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.inject.Inject;

public class PhotoReceiver extends RoboBroadcastReceiver {

	@Inject
	private PictureSuckerServiceModel serviceModel;
	
	@Override
	protected void handleReceive(Context context, Intent intent) {
		Uri contentUri = intent.getData();
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		
		int columnIndex = cursor.getColumnIndexOrThrow(proj[0]);
		cursor.moveToFirst();
		final String filePath = cursor.getString(columnIndex);
		Log.i(getClass().getName(), "Got file from native camera app: " + filePath);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				serviceModel.startUpload(new File(filePath));
			}
			
		}).start();
	}
}
