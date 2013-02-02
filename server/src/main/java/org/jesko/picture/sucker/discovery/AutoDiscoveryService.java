package org.jesko.picture.sucker.discovery;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

@Service
public class AutoDiscoveryService {

	private static final Log log = LogFactory.getLog(AutoDiscoveryService.class);
	
	private final AutoDiscoveryPublisher publisher;

	@Inject
	public AutoDiscoveryService(AutoDiscoveryPublisher publisher) {
		this.publisher = publisher;
	}
	
	@PostConstruct
	public void init() {
		log.info("Started publishing picture sucker service for auto discovery");
		new Thread(new Runnable() {

			@Override
			public void run() {
				publisher.start();
			}
			
		}).start();
	}
}
