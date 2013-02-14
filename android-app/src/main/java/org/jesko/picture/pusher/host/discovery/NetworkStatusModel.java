package org.jesko.picture.pusher.host.discovery;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

@ContextSingleton
public class NetworkStatusModel {

	private ConnectivityManager connectivityManager;
	private WifiManager wifiManager;
	
	@Inject
	public NetworkStatusModel(ConnectivityManager connectivityManager, WifiManager wifiManager) {
		this.connectivityManager = connectivityManager;
		this.wifiManager = wifiManager;
	}
	
	public String getCurrentConnectedSsid() {
		NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo.isConnected()) {
			return wifiManager.getConnectionInfo().getSSID();
		}
		return null;
	}
}
