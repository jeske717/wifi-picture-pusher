package org.jesko.picture.pusher.host;

import java.util.HashSet;
import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.host.discovery.DiscoveryListener;
import org.jesko.picture.pusher.host.discovery.NetworkScanner;

import android.content.SharedPreferences;
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
	@Inject
	private SharedPreferences preferences;
	
	private HostListener listener;
	private Set<HostInfo> hosts = new HashSet<HostInfo>();

	@Override
	public void compatibleServiceFound(String endpoint) {
		HostInfo newHost = hostResolver.resolve(endpoint);
		if(newHost != null) {
			Log.i(getClass().getName(), "Found a host! : " + newHost.getHost());
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
		
		String savedHost = preferences.getString(PREFERRED_HOST_KEY, "");
		if("".equals(savedHost)) {
			networkScanner.start(this);
		} else {
			HostInfo preferredHost = hostResolver.resolve(savedHost);
			hosts.add(preferredHost);
			listener.preferredHostFound(preferredHost);
		}
	}

}
