package org.jesko.picture.pusher.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.jesko.picture.pusher.host.Host;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PictureSuckerServiceModel {

	private final Set<Host> hosts = new HashSet<Host>();
	private final RestTemplate restTemplate;
	
	@Inject
	public PictureSuckerServiceModel(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
	}
	
	public void toggleHost(Host host) {
		if(hosts.contains(host)) {
			hosts.remove(host);
		} else {
			hosts.add(host);
		}
	}

	public void startUpload(String file) {
		Log.i(getClass().getName(), "uploading file from: " + file);
		MultiValueMap<String, Object> upload = new LinkedMultiValueMap<String, Object>();
		upload.add("file", new FileSystemResource(file));
		for (Host host : hosts) {
			try {
				restTemplate.postForLocation(new URI("http://" + host.getHost() + ":" + host.getPort()), upload);
			} catch (RestClientException e) {
				Log.e(getClass().getName(), "error communicating with endpoint", e);
			} catch (URISyntaxException e) {
				Log.e(getClass().getName(), "error communicating with endpoint", e);
			}
		}
	}

}
