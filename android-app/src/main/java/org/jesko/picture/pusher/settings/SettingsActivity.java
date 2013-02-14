package org.jesko.picture.pusher.settings;

import org.jesko.picture.pusher.R;
import org.jesko.picture.pusher.external.PhotoReceiver;

import roboguice.activity.RoboPreferenceActivity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
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
			Preference preference = findPreference(getString(R.string.upload_pictures_from_camera_key));
			
			preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
				
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					setAutoUpload(Boolean.TRUE.equals(newValue));
					return true;
				}
			});
		}
		
		private void setAutoUpload(boolean autoUpload) {
			PackageManager packageManager = getActivity().getPackageManager();
			
			packageManager.setComponentEnabledSetting(new ComponentName(getActivity(), PhotoReceiver.class), 
					autoUpload ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 
							PackageManager.DONT_KILL_APP);
		}
	}
}
