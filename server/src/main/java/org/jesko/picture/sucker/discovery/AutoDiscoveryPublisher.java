package org.jesko.picture.sucker.discovery;

import java.io.IOException;

import javax.inject.Inject;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.jesko.picture.sucker.Main;
import org.springframework.stereotype.Service;

@Service
public class AutoDiscoveryPublisher {

	private final JmDNS jmdns;

	@Inject
	public AutoDiscoveryPublisher(JmDNS jmdns) {
		this.jmdns = jmdns;
	}
	
	public void start() {
		ServiceInfo info = ServiceInfo.create("_http._tcp.local.", "pictureSucker", Main.PORT, "Wifi Picture Sucker");
		try {
			jmdns.registerService(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
