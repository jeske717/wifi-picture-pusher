package org.jesko.picture.pusher;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.provider.MediaStore;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(value = R.layout.main)
public class MainActivity extends RoboActivity {
	
	private static final int PICTURE_REQUEST_CODE = 16;
	
	@Click
	public void takePicture() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, PICTURE_REQUEST_CODE);
	}
	
}
