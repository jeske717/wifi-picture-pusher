package org.jesko.picture.pusher.host;

import java.util.HashSet;
import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.host.discovery.DiscoveryListener;
import org.jesko.picture.pusher.host.discovery.NetworkScanner;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HostModel implements DiscoveryListener {

	public static final String PREFERRED_HOST_KEY = "preferredHost";
	
	@Inject
	private NetworkScanner networkScanner;
	@Inject
	private HostResolver hostResolver;
	
	private HostListener listener;
	private final Set<HostInfo> hosts = new HashSet<HostInfo>();
	private final Set<HostInfo> selectedHosts = new HashSet<HostInfo>();

	@Override
	public void compatibleServiceFound(String endpoint) {
		HostInfo newHost = hostResolver.resolve(endpoint);
		if(newHost != null) {
			Log.i(getClass().getName(), "Found a host! : " + newHost.getAddress());
			hosts.add(newHost);
			listener.newHostFound(hosts);
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
		listener.newHostFound(hosts);
		
		networkScanner.start(this);
	}
	
	public void toggleHost(HostInfo host) {
		if(selectedHosts.contains(host)) {
			selectedHosts.remove(host);
		} else {
			selectedHosts.add(host);
		}
	}
	
	public Set<HostInfo> getSelectedHosts() {
		return selectedHosts;
	}

}
