package org.jesko.picture.pusher;

import java.util.List;

import org.jesko.picture.pusher.host.Host;
import org.jesko.picture.pusher.host.HostListener;
import org.jesko.picture.pusher.host.HostModel;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(value = R.layout.main)
public class MainActivity extends RoboActivity implements HostListener {
	
	@Inject
	private HostModel hostModel;
	
	@Inject
	private HostAdapter hostAdapter;
	
	@ViewById
	ListView hostList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		obtainHost();
	}
	
	@AfterViews
	public void init() {
		hostList.setAdapter(hostAdapter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		hostModel.removeHostListener();
	}

	@Background
	public void obtainHost() {
		hostModel.setHostListener(this);
	}

	@Override
	public void preferredHostFound(Host host) {
		hostAdapter.add(host);
		hostList.setItemChecked(hostAdapter.getPosition(host), true);
	}

	@UiThread
	@Override
	public void newHostFound(List<Host> allHosts) {
		hostAdapter.addAll(allHosts);
	}

	@UiThread
	@Override
	public void errorWithDiscovery(Throwable cause) {
		Toast.makeText(this, getString(R.string.error_with_discovery_service) + ": " + cause.getMessage(), Toast.LENGTH_LONG).show();
	}
	
	@ItemClick(R.id.hostList)
	public void hostClicked(boolean isSelected, Host host) {
	}
	
}
