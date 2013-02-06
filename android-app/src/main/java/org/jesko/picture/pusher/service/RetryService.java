package org.jesko.picture.pusher.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import roboguice.service.RoboService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.inject.Inject;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EService;

@EService
public class RetryService extends RoboService {

	@Inject
	private PictureSuckerServiceModel serviceModel;
	@Inject
	private TransferDao transferDao;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		startRetries();
	}
	
	@Background
	public void startRetries() {
		Set<Transfer> successfulRetries = new HashSet<Transfer>();
		try {
			for (Transfer transfer : transferDao.queryForAll()) {
				serviceModel.startUpload(transfer);
				successfulRetries.add(transfer);
			}
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Unable to query for the outstanding transfers", e);
		}
		
		try {
			transferDao.delete(successfulRetries);
			Log.i(getClass().getName(), "Removing " + successfulRetries.size() + " successful retries");
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Unable to delete transfer", e);
		}
	}
	
}
