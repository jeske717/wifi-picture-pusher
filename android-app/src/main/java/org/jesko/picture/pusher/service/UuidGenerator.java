package org.jesko.picture.pusher.service;

import java.util.UUID;

public class UuidGenerator {

	public String generate() {
		return UUID.randomUUID().toString();
	}
}
