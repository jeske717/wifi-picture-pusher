package org.jesko.picture.sucker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jesko.picture.sucker.bean.HostInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody HostInfo getHostInfo() throws UnknownHostException {
		HostInfo hostInfo = new HostInfo();
		hostInfo.setHost(InetAddress.getLocalHost().getHostAddress());
		hostInfo.setPort(Main.PORT);
		return hostInfo;
	}
	
}
