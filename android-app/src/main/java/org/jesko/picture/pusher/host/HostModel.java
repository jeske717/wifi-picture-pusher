package org.jesko.picture.pusher.host;

import java.util.ArrayList;
import java.util.List;

import org.jesko.picture.pusher.host.discovery.DiscoveryListener;
import org.jesko.picture.pusher.host.discovery.NetworkScanner;

import android.content.SharedPreferences;

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
	private List<Host> hosts = new ArrayList<Host>();
	

	public void addHostListener(HostListener listener) {
		this.listener = listener;
		
		String savedHost = preferences.getString(PREFERRED_HOST_KEY, "");
		if("".equals(savedHost)) {
			networkScanner.start(this);
		} else {
			Host preferredHost = hostResolver.resolve(savedHost);
			hosts.add(preferredHost);
			listener.preferredHostFound(preferredHost);
		}
	}

	@Override
	public void compatibleServiceFound(String endpoint) {
		Host newHost = hostResolver.resolve(endpoint);
		if(newHost != null) {
			hosts.add(newHost);
			listener.newHostFound(hosts);
		}
	}

	@Override
	public void errorOccured(Throwable cause) {
		listener.errorWithDiscovery(cause);
	}

}
