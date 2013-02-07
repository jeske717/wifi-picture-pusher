package org.jesko.picture.pusher;

import roboguice.receiver.RoboBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhotoReceiver extends RoboBroadcastReceiver {

//	@Inject
//	private SharedPreferences preferences;
	
	@Override
	protected void handleReceive(Context context, Intent intent) {
		Log.i("", "receiving message that a new photo has been taken");
	}
}
