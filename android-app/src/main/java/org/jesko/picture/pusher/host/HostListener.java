package org.jesko.picture.pusher.host;

import java.util.List;

import org.jesko.picture.pusher.beans.HostInfo;

public interface HostListener {

	void preferredHostFound(HostInfo host);
	
	void newHostFound(List<HostInfo> allHosts);

	void errorWithDiscovery(Throwable cause);
}
