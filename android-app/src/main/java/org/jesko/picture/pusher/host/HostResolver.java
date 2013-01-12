package org.jesko.picture.pusher.host;

import javax.inject.Inject;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

public class HostResolver {

	private final RestTemplate restTemplate;

	@Inject
	public HostResolver(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}
	
	public Host resolve(String endpoint) {
		try {
			return restTemplate.getForObject(endpoint, Host.class);
		} catch(Exception e) {
			Log.e(HostResolver.class.getName(), "Couldn't get host info from: " + endpoint, e);
			return null;
		}
	}

}
