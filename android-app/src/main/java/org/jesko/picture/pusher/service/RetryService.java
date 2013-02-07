package org.jesko.picture.pusher.service;

import java.sql.SQLException;

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
		startRetry();
	}
	
	@Background
	public void startRetry() {
		try {
			for (Transfer transfer : transferDao.queryForAll()) {
				serviceModel.startUpload(transfer);
				transferDao.delete(transfer);
			}
		} catch (SQLException e) {
			Log.e(getClass().getName(), "Unable to query the database", e);
		}
	}
	
}
