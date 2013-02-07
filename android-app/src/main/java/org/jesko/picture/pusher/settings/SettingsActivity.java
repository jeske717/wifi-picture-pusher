package org.jesko.picture.pusher.settings;

import org.jesko.picture.pusher.R;

import roboguice.activity.RoboPreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;
import com.googlecode.androidannotations.annotations.OptionsMenu;

@EActivity
@OptionsMenu(R.menu.settings_menu)
public class SettingsActivity extends RoboPreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
	
	@OptionsItem
	public void acceptSettings() {
		finish();
	}
	
	public static class SettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings);
		}
	}
}
