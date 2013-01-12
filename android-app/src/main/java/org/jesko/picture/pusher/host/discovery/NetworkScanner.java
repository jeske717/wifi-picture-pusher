package org.jesko.picture.pusher.host.discovery;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NetworkScanner {

	private static final String SERVICE_PREFIX = "pictureSucker";
	private static final String SERVICE_TYPE = "_http._tcp.local.";
	private static final String MULTICAST_LOCK_TAG = "wifiPicturePusherDiscoverer";
	
	@Inject
	private WifiManager wifiManager;
	
	private MulticastLock lock;
	private JmDNS jmDns;
	private ServiceListener serviceListener;
	private DiscoveryListener listener;
	
	public void start(DiscoveryListener listener) {
		this.listener = listener;
		
		lock = wifiManager.createMulticastLock(MULTICAST_LOCK_TAG);
		lock.setReferenceCounted(true);
		lock.acquire();
		
		try {
			jmDns = JmDNS.create();
			serviceListener = new PictureSuckerServiceListener();
			
			jmDns.addServiceListener(SERVICE_TYPE, serviceListener);
		} catch (IOException e) {
			listener.errorOccured(e);
		}
	}
	
	public void stop() {
		jmDns.removeServiceListener(SERVICE_TYPE, serviceListener);
		try {
			jmDns.close();
		} catch(IOException e) {
			listener.errorOccured(e);
		}
		lock.release();
		listener = null;
	}
	
	private class PictureSuckerServiceListener implements ServiceListener {
		
		@Override
		public void serviceAdded(ServiceEvent event) {
			if(event.getName().startsWith(SERVICE_PREFIX)) {
				jmDns.requestServiceInfo(event.getType(), event.getName());
			}
		}
		
		@Override
		public void serviceResolved(ServiceEvent event) {
			listener.compatibleServiceFound(event.getInfo().getHostAddresses()[0] + event.getInfo().getPort());
		}
		
		@Override
		public void serviceRemoved(ServiceEvent event) {
		}
	}
}
