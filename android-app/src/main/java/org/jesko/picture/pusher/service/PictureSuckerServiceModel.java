package org.jesko.picture.pusher.service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.beans.UploadResult;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PictureSuckerServiceModel {

	private final Set<HostInfo> hosts = new HashSet<HostInfo>();
	private final RestTemplate restTemplate;
	private UploadListener uploadListener;
	private final RetryModel retryModel;
	
	@Inject
	public PictureSuckerServiceModel(RestTemplate restTemplate, RetryModel retryModel) {
		this.restTemplate = restTemplate;
		this.retryModel = retryModel;
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}
	
	public void toggleHost(HostInfo host) {
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
		for (HostInfo host : hosts) {
			String finishedMessage = "";
			try {
				URI destination = new URI("http://" + host.getAddress() + ":" + host.getPort() + "/upload");
				Log.i(getClass().getName(), "Posting file to destination: " + destination);
				UploadResult result = restTemplate.postForObject(destination, upload, UploadResult.class);
				Log.i(getClass().getName(), "Result: " + result.getResult());
				finishedMessage = result.getResult();
			} catch (RestClientException e) {
				Log.e(getClass().getName(), Log.getStackTraceString(e));
				finishedMessage = e.getMessage();
				retryModel.uploadFailed(file, host);
			} catch (URISyntaxException e) {
				Log.e(getClass().getName(), Log.getStackTraceString(e));
				finishedMessage = e.getMessage();
			}
			uploadListener.uploadCompleted("Picture transfer complete: " + finishedMessage);
		}
	}

	public void setUploadListener(UploadListener listener) {
		this.uploadListener = listener;
	}
	
	public void removeUploadListener() {
		this.uploadListener = null;
	}

}
