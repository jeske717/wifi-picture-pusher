package org.jesko.picture.sucker.discovery;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class AutoDiscoveryService {

	private final AutoDiscoveryPublisher publisher;

	@Inject
	public AutoDiscoveryService(AutoDiscoveryPublisher publisher) {
		this.publisher = publisher;
	}
	
	@PostConstruct
	public void init() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				publisher.start();
			}
			
		}).start();
	}
}
