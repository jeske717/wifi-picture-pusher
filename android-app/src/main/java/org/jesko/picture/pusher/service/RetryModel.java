package org.jesko.picture.pusher.service;

import java.io.File;

import org.jesko.picture.pusher.beans.HostInfo;

import com.google.inject.Singleton;

@Singleton
public class RetryModel {

	public void uploadFailed(File file, HostInfo host) {
	}

}
