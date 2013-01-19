package org.jesko.picture.pusher.service;

import java.io.File;
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

	public void startUpload(File file) {
		Log.i(getClass().getName(), "Requested upload from: " + file);
		MultiValueMap<String, Object> upload = new LinkedMultiValueMap<String, Object>();
		upload.add("file", new FileSystemResource(file));
		for (Host host : hosts) {
			try {
				URI destination = new URI("http://" + host.getHost() + ":" + host.getPort() + "/upload");
				Log.i(getClass().getName(), "Posting file to destination: " + destination);
				restTemplate.postForLocation(destination, upload);
			} catch (RestClientException e) {
				Log.e(getClass().getName(), Log.getStackTraceString(e));
			} catch (URISyntaxException e) {
				Log.e(getClass().getName(), Log.getStackTraceString(e));
			}
		}
	}

}
