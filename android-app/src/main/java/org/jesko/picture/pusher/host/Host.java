package org.jesko.picture.pusher.host;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "hosts", daoClass = HostDao.class)
public class Host {

	@DatabaseField(generatedId = true)
	private long id;
	
	@DatabaseField
	private String ssid;
	
	@DatabaseField
	private String address;
	
	@DatabaseField
	private String hostName;
	
	@DatabaseField
	private int port;

	public long getId() {
		return id;
	}

	public String getSsid() {
		return ssid;
	}

	public String getAddress() {
		return address;
	}

	public String getHostName() {
		return hostName;
	}

	public int getPort() {
		return port;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
