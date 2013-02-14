package org.jesko.picture.pusher.external;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.inject.Inject;

@ContextSingleton
public class MediaStoreHelper {

	@Inject
	private Context context;
	
	public String getFilePathFromUri(Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(context, uri, proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		
		int columnIndex = cursor.getColumnIndexOrThrow(proj[0]);
		cursor.moveToFirst();
		final String filePath = cursor.getString(columnIndex);
		return filePath;
	}
}
