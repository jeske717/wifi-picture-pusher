package org.jesko.picture.pusher;

import java.io.File;

import org.jesko.picture.pusher.service.ImageNamer;
import org.jesko.picture.pusher.service.PictureSuckerServiceModel;

import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.picture)
public class PictureActivity extends RoboActivity {

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
			ImageView imageView = new ImageView(this);
			imageView.setImageBitmap(BitmapFactory.decodeFile(imageNamer.getCurrentFile().getAbsolutePath()));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
			
			thumbnailView.addView(imageView);
			
			startUpload(imageNamer.getCurrentFile());
		}
	}
	
	@Background
	public void startUpload(File file) {
		serviceModel.startUpload(file);
	}
}
