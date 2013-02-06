package org.jesko.picture.pusher.host;

import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;

public interface HostListener {

	void newHostFound(Set<HostInfo> allHosts);

	void errorWithDiscovery(Throwable cause);
}
