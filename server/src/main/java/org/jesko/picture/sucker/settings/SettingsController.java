package org.jesko.picture.sucker.settings;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingsController {

	@Inject
	private SettingsModel settingsModel;
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public @ResponseBody Settings getSettings() {
		return settingsModel.getSettings();
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public @ResponseBody Settings saveSettings(@RequestBody Settings settings) {
		settingsModel.setSettings(settings);
		return settingsModel.getSettings();
	}
}
