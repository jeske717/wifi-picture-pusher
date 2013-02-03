package org.jesko.picture.pusher;

import org.jesko.picture.pusher.beans.HostInfo;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.inject.Inject;

@ContextSingleton
public class HostAdapter extends ArrayAdapter<HostInfo>{

	@Inject
	public HostAdapter(Context context) {
		super(context, android.R.layout.simple_list_item_multiple_choice);
	}

}
