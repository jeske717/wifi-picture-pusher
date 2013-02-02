package org.jesko.picture.sucker;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jesko.picture.sucker.bean.HostInfo;
import org.jesko.picture.sucker.bean.UploadResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WebController {

	private static final Log log = LogFactory.getLog(WebController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody HostInfo getHostInfo() throws UnknownHostException {
		HostInfo hostInfo = new HostInfo();
		hostInfo.setHost(InetAddress.getLocalHost().getHostAddress());
		hostInfo.setPort(Main.PORT);
		return hostInfo;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody UploadResult uploadImage(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		file.transferTo(new File("/Users/ben/", file.getOriginalFilename()));
		log.info("uploaded file of size: " + file.getSize() + " received");
		UploadResult uploadResult = new UploadResult();
		uploadResult.setResult("SUCCESS");
		return uploadResult;
	}
	
}
