package org.jesko.picture.pusher.service;

import java.io.File;
import java.sql.SQLException;

import org.jesko.picture.pusher.R;
import org.jesko.picture.pusher.beans.HostInfo;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class RetryModel {
	
	private final SharedPreferences preferences;
	private final TransferDao transferDao;
	private final Context context;

	@Inject
	public RetryModel(Context context, SharedPreferences preferences, TransferDao transferDao) {
		this.context = context;
		this.preferences = preferences;
		this.transferDao = transferDao;
	}
	
	public void uploadFailed(File file, HostInfo host) {
		boolean shouldPersist = preferences.getBoolean(context.getString(R.string.retry_send_on_failure_key), false);
		
		if(shouldPersist) {
			Log.i(getClass().getName(), "Persisting failed transfer for retry");
			Transfer data = new Transfer();
			data.setFileName(file.getAbsolutePath());
			data.setEndpoint("http://" + host.getAddress() + ":" + host.getPort() + "/upload");
			try {
				transferDao.create(data);
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Failed to persist the failed transfer", e);
			}
		}
	}

}
