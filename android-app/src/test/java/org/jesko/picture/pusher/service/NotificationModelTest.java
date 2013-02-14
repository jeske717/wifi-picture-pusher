package org.jesko.picture.pusher.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.jesko.picture.pusher.PictureActivity;
import org.jesko.picture.pusher.R;
import org.jesko.picture.pusher.external.PhotoGalleryUploadActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NotificationModelTest {

	@Mock
	private NotificationManager notificationManager;
	@Mock
	private SharedPreferences sharedPreferences;
	
	private NotificationModel testObject;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void notifyImageUploadedIsNoOpWhenContextIsPictureActivity() {
		Context context = mock(PictureActivity.class);
		testObject = new NotificationModel(context, notificationManager, sharedPreferences);
		
		testObject.notifyImageUploaded();
		
		verifyZeroInteractions(notificationManager);
	}
	
	@Test
	public void notifyImageUploadedIsNoOpWhenContextIsPhotoGalleryUploadActivity() {
		Context context = mock(PhotoGalleryUploadActivity.class);
		testObject = new NotificationModel(context, notificationManager, sharedPreferences);
		
		testObject.notifyImageUploaded();
		
		verifyZeroInteractions(notificationManager);
	}
	
	@Test
	public void notifyImageUploadedIsNoOpWhenShowNotificationsPreferenceIsOff() {
		Context context = mock(RetryService.class);
		when(context.getString(R.string.show_notifications_on_success_key)).thenReturn("prefkey");
		when(sharedPreferences.getBoolean("prefkey", false)).thenReturn(Boolean.FALSE);
		testObject = new NotificationModel(context, notificationManager, sharedPreferences);
		
		testObject.notifyImageUploaded();
		
		verifyZeroInteractions(notificationManager);
	}

}
