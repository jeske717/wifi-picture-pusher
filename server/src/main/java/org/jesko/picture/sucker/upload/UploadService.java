package org.jesko.picture.sucker.upload;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jesko.picture.sucker.settings.SettingsModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

	private static final Log log = LogFactory.getLog(UploadService.class);
	
	@Inject
	private SettingsModel settingsModel;
	
	public void saveFile(MultipartFile file) throws IllegalStateException, IOException {
		String imageDirectory = settingsModel.getSettings().getImageDirectory();
		log.info("Uploaded file of size: " + file.getSize() + " received, saving to: [" + imageDirectory + "]");
		
		file.transferTo(new File(imageDirectory, file.getOriginalFilename()));
	}
	
}
