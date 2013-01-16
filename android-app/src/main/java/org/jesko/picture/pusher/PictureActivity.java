package org.jesko.picture.pusher;

import org.jesko.picture.pusher.service.PictureSuckerServiceModel;

import roboguice.activity.RoboActivity;

import android.os.Bundle;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity(R.layout.picture)
public class PictureActivity extends RoboActivity {

	@Inject
	private PictureSuckerServiceModel serviceModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Click
	public void takePicture() {
		
	}
}
