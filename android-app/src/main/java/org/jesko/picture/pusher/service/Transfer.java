package org.jesko.picture.pusher.service;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transfers", daoClass = TransferDao.class)
public class Transfer implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private long id;
	
	@DatabaseField
	private String fileName;
	
	@DatabaseField
	private String address;
	
	@DatabaseField
	private int port;

	public long getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setAddress(String endpoint) {
		this.address = endpoint;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
}
