package org.jesko.picture.pusher.host;

import java.sql.SQLException;
import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.host.discovery.DiscoveryListener;
import org.jesko.picture.pusher.host.discovery.NetworkScanner;

import roboguice.inject.ContextSingleton;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class HostModel implements DiscoveryListener {

	public static final String PREFERRED_HOST_KEY = "preferredHost";
	
	@Inject
	private NetworkScanner networkScanner;
	@Inject
	private HostResolver hostResolver;
	@Inject
	private HostDao hostDao;
	@Inject
	private HostCollectionModel hostCollectionModel;
	@Inject
	private HostAdapter hostAdapter;
	
	private HostListener listener;

	@Override
	public void compatibleServiceFound(String endpoint) {
		HostInfo newHost = hostResolver.resolve(endpoint);
		if(newHost != null) {
			Log.i(getClass().getName(), "Found a host!: " + newHost.getAddress());
			hostCollectionModel.addHost(newHost);
			listener.newHostFound(hostCollectionModel.getAll());
		}
	}

	@Override
	public void errorOccured(Throwable cause) {
		listener.errorWithDiscovery(cause);
	}

	public void removeHostListener() {
		networkScanner.stop();
		listener = null;
	}
	
	public void setHostListener(HostListener listener) {
		this.listener = listener;
		try {
			if(hostDao.countOf() > 0) {
				listener.savedHostsFound();
				return;
			}
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Unable to query for persisted hosts", e);
		}
		listener.newHostFound(hostCollectionModel.getAll());

		start();
	}
	
	public void start() {
		networkScanner.start(this);
	}
	
	public void toggleHost(HostInfo host) {
		hostCollectionModel.toggleHostInfo(host);
	}
	
	public Set<HostInfo> getSelectedHosts() {
		try {
			return hostAdapter.adapt(hostDao.queryForAll());
		} catch (SQLException e) {
			return hostCollectionModel.getSelectedHosts();
		}
	}

	public void commit() {
		Set<HostInfo> hostsToSave = hostCollectionModel.getSelectedHosts();
		boolean successful = true;
		for (Host host : hostAdapter.adapt(hostsToSave)) {
			try {
				hostDao.create(host);
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Couldn't persist host", e);
				successful = false;
			}
		}
		if(successful) {
			hostCollectionModel.clearAll();
		}
	}
}
