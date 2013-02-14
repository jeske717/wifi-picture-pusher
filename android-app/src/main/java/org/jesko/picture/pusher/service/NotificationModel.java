package org.jesko.picture.pusher.service;

import org.jesko.picture.pusher.PictureActivity;
import org.jesko.picture.pusher.PictureActivity_;
import org.jesko.picture.pusher.R;
import org.jesko.picture.pusher.external.PhotoGalleryUploadActivity;

import roboguice.inject.ContextSingleton;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.inject.Inject;

@ContextSingleton
public class NotificationModel {

	private static final int NOTIFICATION_ID = 7823;
	
	private final Context context;
	private final NotificationManager notificationManager;
	private final SharedPreferences sharedPreferences;
	
	@Inject
	public NotificationModel(Context context, NotificationManager notificationManager, SharedPreferences sharedPreferences) {
		this.context = context;
		this.notificationManager = notificationManager;
		this.sharedPreferences = sharedPreferences;
	}

	public void notifyImageUploaded() {
		if(shouldDisplayNotification()) {
			Intent intent = PictureActivity_.intent(context).get();
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			Notification.Builder builder = new Notification.Builder(context);
			builder.setContentTitle("Image upload complete").setContentText("One or more images have been uploaded").setContentIntent(pendingIntent).setSmallIcon(R.drawable.app_icon);
			Notification notification = builder.getNotification();
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(NOTIFICATION_ID, notification);
		}
	}

	private boolean shouldDisplayNotification() {
		return !(context instanceof PictureActivity) 
				&& !(context instanceof PhotoGalleryUploadActivity) 
				&& sharedPreferences.getBoolean(context.getString(R.string.show_notifications_on_success_key), false);
	}
}
