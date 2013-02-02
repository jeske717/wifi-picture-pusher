package org.jesko.picture.sucker.settings;

public class Settings {

	private String imageDirectory;

	public String getImageDirectory() {
		return imageDirectory;
	}

	public void setImageDirectory(String imageDirectory) {
		this.imageDirectory = imageDirectory;
	}

	public static Settings defaults() {
		return DefaultSettingsHolder.SETTINGS;
	}
	
	private static class DefaultSettingsHolder {
		
		private static Settings SETTINGS = new Settings() {
			
			public String getImageDirectory() {
				return System.getProperty("user.home");
			}
			
		};
	}
	
}
