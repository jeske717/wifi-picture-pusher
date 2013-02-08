package org.jesko.picture.pusher.data;

import java.util.Arrays;
import java.util.List;

import org.jesko.picture.pusher.host.Host;
import org.jesko.picture.pusher.service.Transfer;

public interface Entities {

	@SuppressWarnings("unchecked")
	static List<Class<?>> ENTITY_CLASSES = Arrays.asList(Transfer.class, Host.class);
}
