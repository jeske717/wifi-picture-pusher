package org.jesko.picture.pusher;

import java.util.List;

import org.jesko.picture.pusher.host.Host;
import org.jesko.picture.pusher.host.HostListener;
import org.jesko.picture.pusher.host.HostModel;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;

@EActivity(value = R.layout.main)
public class MainActivity extends RoboActivity implements HostListener {
	
	private static final int PICTURE_REQUEST_CODE = 16;
	
	@Inject
	private HostModel hostModel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		obtainHost();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		hostModel.removeHostListener();
	}

	@Click
	public void takePicture() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, PICTURE_REQUEST_CODE);
	}
	
	@Background
	public void obtainHost() {
		hostModel.setHostListener(this);
	}

	@Override
	public void preferredHostFound(Host host) {
	}

	@Override
	public void newHostFound(List<Host> allHosts) {
	}

	@UiThread
	@Override
	public void errorWithDiscovery(Throwable cause) {
		Toast.makeText(this, getString(R.string.error_with_discovery_service) + ": " + cause.getMessage(), Toast.LENGTH_LONG).show();
	}
	
}
