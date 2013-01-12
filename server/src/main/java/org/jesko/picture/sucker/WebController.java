package org.jesko.picture.sucker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;

import org.jesko.picture.sucker.discovery.AutoDiscoveryPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

	@Inject
	public WebController(final AutoDiscoveryPublisher publisher) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				publisher.start();
			}
			
		}).start();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody HostInfo getHostInfo() throws UnknownHostException {
		HostInfo hostInfo = new HostInfo();
		hostInfo.setHost(InetAddress.getLocalHost().toString());
		hostInfo.setPort(Main.PORT);
		return hostInfo;
	}
	
}
