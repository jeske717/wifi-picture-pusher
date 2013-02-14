package org.jesko.picture.pusher.external;

import java.io.File;

import org.jesko.picture.pusher.PictureActivity_;
import org.jesko.picture.pusher.service.PictureSuckerServiceModel;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;

@EActivity
public class PhotoGalleryUploadActivity extends RoboActivity {

	@Inject
	private PictureSuckerServiceModel pictureSuckerServiceModel;
	@Inject
	private MediaStoreHelper mediaStoreHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		if(Intent.ACTION_SEND.equals(intent.getAction())) {
			Bundle extras = intent.getExtras();
			if(extras.containsKey(Intent.EXTRA_STREAM)) {
				Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
				upload(mediaStoreHelper.getFilePathFromUri(uri));
			}
		}
		
		PictureActivity_.intent(this).start();
	}
	
	@Background
	public void upload(String filePath) {
		pictureSuckerServiceModel.startUpload(new File(filePath));
	}

}
