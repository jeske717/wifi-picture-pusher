package org.jesko.picture.pusher.host;

import java.util.List;

public interface HostListener {

	void preferredHostFound(Host host);
	
	void newHostFound(List<Host> allHosts);

	void errorWithDiscovery(Throwable cause);
}
