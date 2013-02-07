package org.jesko.picture.pusher.service;

import java.io.File;
import java.sql.SQLException;

import org.jesko.picture.pusher.R;
import org.jesko.picture.pusher.beans.HostInfo;

import roboguice.inject.ContextSingleton;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class RetryModel {
	
	private final SharedPreferences preferences;
	private final TransferDao transferDao;
	private final Context context;
	private final AlarmManager alarmManager;

	@Inject
	public RetryModel(Context context, SharedPreferences preferences, TransferDao transferDao, AlarmManager alarmManager) {
		this.context = context;
		this.preferences = preferences;
		this.transferDao = transferDao;
		this.alarmManager = alarmManager;
	}
	
	public void uploadFailed(File file, HostInfo host) {
		boolean shouldPersist = preferences.getBoolean(context.getString(R.string.retry_send_on_failure_key), false);
		
		if(shouldPersist) {
			Log.i(getClass().getName(), "Persisting failed transfer for retry");
			Transfer data = new Transfer();
			data.setFileName(file.getAbsolutePath());
			data.setAddress(host.getAddress());
			data.setPort(host.getPort());
			try {
				transferDao.create(data);
				alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 15000, getRetryServiceIntent());
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Failed to persist the failed transfer", e);
			}
		}
	}

	private PendingIntent getRetryServiceIntent() {
		return PendingIntent.getService(context, 0, RetryService_.intent(context).get(), PendingIntent.FLAG_UPDATE_CURRENT);
	}

}
