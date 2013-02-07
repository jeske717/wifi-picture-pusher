package org.jesko.picture.pusher;

import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.host.HostListener;
import org.jesko.picture.pusher.host.HostModel;
import org.jesko.picture.pusher.settings.SettingsActivity_;

import roboguice.activity.RoboActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(value = R.layout.main)
@OptionsMenu(R.menu.main_menu)
public class MainActivity extends RoboActivity implements HostListener {
	
	@Inject
	private HostModel hostModel;
	@Inject
	private HostAdapter hostAdapter;
	
	private ProgressDialog loaderDialog;
	@ViewById
	ListView hostList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		obtainHost();
		
		if(!isReconfiguring()) {
			loaderDialog = new ProgressDialog(this);
			loaderDialog.setTitle("Searching for picture suckers on the network");
			loaderDialog.setMessage("Searching...");
			loaderDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loaderDialog.show();
		}
	}
	
	@AfterViews
	public void init() {
		hostList.setAdapter(hostAdapter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		removeHostListener();
	}
	
	@OptionsItem
	public void settingsMenu() {
		SettingsActivity_.intent(this).start();
	}

	@Background
	public void obtainHost() {
		hostModel.setHostListener(this);
	}
	
	@Background
	public void obtainHostsForReconfiguration() {
		addSelectedHosts(hostModel.getSelectedHosts());
		hostModel.start();
	}
	
	@UiThread
	public void addSelectedHosts(Set<HostInfo> selectedHostInfos) {
		hostAdapter.addAll(selectedHostInfos);
		for(int i = 0; i < hostAdapter.getCount(); i++) {
			hostList.setItemChecked(i, true);
		}
	}

	@UiThread
	@Override 
	public void newHostFound(Set<HostInfo> allHosts) {
		for (HostInfo hostInfo : allHosts) {
			if (hostAdapter.getPosition(hostInfo) < 0) {
				hostAdapter.add(hostInfo);
			}
		}
		if (allHosts.size() > 0 && loaderDialog != null) {
			loaderDialog.dismiss();
		}
	}

	@UiThread
	@Override
	public void savedHostsFound() {
		if (isReconfiguring()) {
			obtainHostsForReconfiguration();
		} else {
			hostsSelected();
		}
	}

	@UiThread
	@Override
	public void errorWithDiscovery(Throwable cause) {
		Toast.makeText(this, getString(R.string.error_with_discovery_service) + ": " + cause.getMessage(), Toast.LENGTH_LONG).show();
	}
	
	@ItemClick(R.id.hostList)
	public void hostClicked(HostInfo host) {
		hostModel.toggleHost(host);
	}
	
	@Click
	public void hostsSelected() {
		hostModel.commit();
		PictureActivity_.intent(this).start();
	}

	@Background
	void removeHostListener() {
		hostModel.removeHostListener();
	}
	
	private boolean isReconfiguring() {
		return getIntent().getExtras() != null && getIntent().getExtras().getBoolean(getString(R.string.reconfigure_hosts));
	}
}
