package org.jesko.picture.pusher.beans;

public class HostInfo {

	private String host;
	private int port;

	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
	
	@Override
	public String toString() {
		return host + ":" + port;
	}
}
