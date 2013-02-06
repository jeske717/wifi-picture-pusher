package org.jesko.picture.pusher.service;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.beans.UploadResult;
import org.jesko.picture.pusher.host.HostModel;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import roboguice.inject.ContextSingleton;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class PictureSuckerServiceModel {

	private final RestTemplate restTemplate;
	private UploadListener uploadListener;
	private final RetryModel retryModel;
	private final HostModel hostModel;
	
	@Inject
	public PictureSuckerServiceModel(RestTemplate restTemplate, RetryModel retryModel, HostModel hostModel) {
		this.restTemplate = restTemplate;
		this.retryModel = retryModel;
		this.hostModel = hostModel;
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}
	
	public void startUpload(File file) {
		MultiValueMap<String, Object> upload = createUploadable(file);
		for (HostInfo host : hostModel.getSelectedHosts()) {
			String finishedMessage = "";
			try {
				UploadResult result = postData(upload, host);
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
	
	public void startUpload(Transfer transfer) {
		HostInfo info = new HostInfo();
		info.setAddress(transfer.getAddress());
		info.setPort(transfer.getPort());
		
		try {
			postData(createUploadable(new File(transfer.getFileName())), info);
		} catch (URISyntaxException e) {
		}
	}

	public void setUploadListener(UploadListener listener) {
		this.uploadListener = listener;
	}
	
	public void removeUploadListener() {
		this.uploadListener = null;
	}

	private UploadResult postData(MultiValueMap<String, Object> upload,
			HostInfo host) throws URISyntaxException {
		URI destination = new URI("http://" + host.getAddress() + ":" + host.getPort() + "/upload");
		Log.i(getClass().getName(), "Posting file to destination: " + destination);
		UploadResult result = restTemplate.postForObject(destination, upload, UploadResult.class);
		Log.i(getClass().getName(), "Result: " + result.getResult());
		return result;
	}
	
	private MultiValueMap<String, Object> createUploadable(File file) {
		Log.i(getClass().getName(), "Requested upload from: " + file);
		MultiValueMap<String, Object> upload = new LinkedMultiValueMap<String, Object>();
		upload.add("file", new FileSystemResource(file));
		return upload;
	}

}
