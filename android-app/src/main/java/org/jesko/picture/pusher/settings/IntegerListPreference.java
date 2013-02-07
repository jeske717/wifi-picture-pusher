package org.jesko.picture.pusher.settings;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;

public class IntegerListPreference extends ListPreference {

	public IntegerListPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public IntegerListPreference(Context context) {
		super(context);
	}

	@Override
	protected boolean persistString(String value) {
		if(value == null) {
			return false;
		}
		return persistInt(Integer.valueOf(value));
	}
	
	@Override
	protected String getPersistedString(String defaultReturnValue) {
		if(getSharedPreferences().contains(getKey())) {
			int intValue = getPersistedInt(60000);
			return String.valueOf(intValue);
		}
		return defaultReturnValue;
	}
}