package org.jesko.picture.pusher.host;

public class Host {

	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return host + ":" + port;
	}
}
