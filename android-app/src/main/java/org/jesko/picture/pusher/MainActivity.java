package org.jesko.picture.pusher;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends RoboActivity {
	
	private static final int PICTURE_REQUEST_CODE = 16;
	
	@InjectView(R.id.button1)
	private Button takePictureButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		takePictureButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, PICTURE_REQUEST_CODE);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
