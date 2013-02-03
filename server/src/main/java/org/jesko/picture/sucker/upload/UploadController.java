package org.jesko.picture.sucker.upload;

import java.io.IOException;

import javax.inject.Inject;

import org.jesko.picture.pusher.beans.UploadResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	@Inject
	private UploadService uploadService;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResult uploadImage(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		uploadService.saveFile(file);
		UploadResult uploadResult = new UploadResult();
		uploadResult.setResult("SUCCESS");
		return uploadResult;
	}
}
