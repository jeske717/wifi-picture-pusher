package org.jesko.picture.sucker.settings;

import org.springframework.stereotype.Component;

@Component
public class SettingsModel {

	private Settings settings;
	
	public Settings getSettings() {
		if(settings != null) {
			return settings;
		}
		return Settings.defaults();
	}
	
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}
