package org.jesko.picture.pusher;

import java.io.File;

import org.jesko.picture.pusher.service.ImageNamer;
import org.jesko.picture.pusher.service.PictureSuckerServiceModel;
import org.jesko.picture.pusher.service.UploadListener;

import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.picture)
public class PictureActivity extends RoboActivity implements UploadListener {

	private static final int REQUEST_CODE = 1;
	
	@Inject
	private PictureSuckerServiceModel serviceModel;
	@Inject
	private ImageNamer imageNamer;
	
	@ViewById
	GridLayout thumbnailView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		serviceModel.setUploadListener(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		serviceModel.removeUploadListener();
	}
	
	@Click
	public void takePicture() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageNamer.generateNextFile()));
		startActivityForResult(cameraIntent, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			startUpload(imageNamer.getCurrentFile());
		}
	}
	
	@Background
	public void startUpload(File file) {
		serviceModel.startUpload(file);
	}

	@UiThread
	@Override
	public void uploadCompleted(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}
}
