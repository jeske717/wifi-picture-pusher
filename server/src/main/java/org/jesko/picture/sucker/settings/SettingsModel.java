package org.jesko.picture.sucker.settings;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SettingsModel {

	private static final Log log = LogFactory.getLog(SettingsModel.class);
	
	@Inject
	private ObjectMapper objectMapper;
	
	private File configFile = new File(new File("."), "config.json");
	private Settings settings;

	@PostConstruct
	public void reloadFromDisk() {
		try {
			setSettings(objectMapper.readValue(configFile, Settings.class));
			log.info("Loaded settings from disk: " + settings);
		} catch (JsonParseException e) {
			log.error("Failed to read config file", e);
		} catch (JsonMappingException e) {
			log.error("Failed to read config file", e);
		} catch (IOException e) {
			log.error("Failed to read config file", e);
		}
	}
	
	public Settings getSettings() {
		if(settings != null) {
			return settings;
		}
		return Settings.defaults();
	}
	
	public void setSettings(Settings settings) {
		this.settings = settings;
		try {
			objectMapper.writeValue(configFile, settings);
		} catch (JsonGenerationException e) {
			log.error("Failed to write config file", e);
		} catch (JsonMappingException e) {
			log.error("Failed to write config file", e);
		} catch (IOException e) {
			log.error("Failed to write config file", e);
		}
	}
}
