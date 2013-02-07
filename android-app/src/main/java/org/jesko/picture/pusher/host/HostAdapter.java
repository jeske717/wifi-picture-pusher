package org.jesko.picture.pusher.host;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.host.discovery.NetworkStatusModel;

import roboguice.inject.ContextSingleton;

import com.google.inject.Inject;

@ContextSingleton
public class HostAdapter {

	@Inject
	private NetworkStatusModel networkStatusModel;
	
	public Set<Host> adapt(Set<HostInfo> hostsToSave) {
		Set<Host> result = new HashSet<Host>();
		for (HostInfo hostInfo : hostsToSave) {
			Host host = new Host();
			host.setAddress(hostInfo.getAddress());
			host.setHostName(hostInfo.getHostName());
			host.setPort(hostInfo.getPort());
			host.setSsid(networkStatusModel.getCurrentConnectedSsid());
			
			result.add(host);
		}
		return result;
	}

	public Set<HostInfo> adapt(List<Host> persistedHosts) {
		Set<HostInfo> result = new HashSet<HostInfo>();
		for (Host host : persistedHosts) {
			HostInfo info = new HostInfo();
			info.setAddress(host.getAddress());
			info.setPort(host.getPort());
			info.setHostName(host.getHostName());
			
			result.add(info);
		}
		return result;
	}

}
