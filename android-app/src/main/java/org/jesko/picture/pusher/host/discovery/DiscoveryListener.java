package org.jesko.picture.pusher.host.discovery;

public interface DiscoveryListener {

	void compatibleServiceFound(String endpoint);
	
	void errorOccured(Throwable cause);
}
