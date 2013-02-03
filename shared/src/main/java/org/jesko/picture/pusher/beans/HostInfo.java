package org.jesko.picture.pusher.beans;

public class HostInfo {

	private String address;
	private String hostName;
	private int port;
	
	public String getHostName() {
		return hostName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return hostName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HostInfo other = (HostInfo) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
}
