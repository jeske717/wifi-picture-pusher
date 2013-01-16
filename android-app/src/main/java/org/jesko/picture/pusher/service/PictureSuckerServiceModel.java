package org.jesko.picture.pusher.service;

import java.util.HashSet;
import java.util.Set;

import org.jesko.picture.pusher.host.Host;

import com.google.inject.Singleton;

@Singleton
public class PictureSuckerServiceModel {

	private final Set<Host> hosts = new HashSet<Host>();
	
	public void toggleHost(Host host) {
		if(hosts.contains(host)) {
			hosts.remove(host);
		} else {
			hosts.add(host);
		}
	}

}
