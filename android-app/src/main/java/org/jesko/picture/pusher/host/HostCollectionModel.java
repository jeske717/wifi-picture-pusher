package org.jesko.picture.pusher.host;

import java.util.HashSet;
import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;

import com.google.inject.Singleton;

@Singleton
public class HostCollectionModel {

	private Set<HostInfo> selectedHosts = new HashSet<HostInfo>();
	private Set<HostInfo> allHosts = new HashSet<HostInfo>();
	
	public void addHost(HostInfo hostInfo) {
		allHosts.add(hostInfo);
	}
	
	public Set<HostInfo> getAll() {
		return allHosts;
	}
	
	public void toggleHostInfo(HostInfo hostInfo) {
		if(selectedHosts.contains(hostInfo)) {
			selectedHosts.remove(hostInfo);
		} else {
			selectedHosts.add(hostInfo);
		}
	}
	
	public Set<HostInfo> getSelectedHosts() {
		return selectedHosts;
	}

	public void clearAll() {
		allHosts.clear();
		selectedHosts.clear();
	}
}
