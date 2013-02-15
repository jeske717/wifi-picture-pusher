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
	private final NotificationModel notificationModel;
	
	@Inject
	public PictureSuckerServiceModel(RestTemplate restTemplate, RetryModel retryModel, HostModel hostModel, NotificationModel notificationModel) {
		this.restTemplate = restTemplate;
		this.retryModel = retryModel;
		this.hostModel = hostModel;
		this.notificationModel = notificationModel;
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}
	
	public void startUpload(File file) {
		MultiValueMap<String, Object> upload = createUploadable(file);
		for (HostInfo host : hostModel.getSelectedHosts()) {
			String message = postData(upload, host);
			if (uploadListener != null) {
				uploadListener.uploadCompleted(message);
			}
		}
	}
	
	public void startUpload(Transfer transfer) {
		HostInfo info = new HostInfo();
		info.setAddress(transfer.getAddress());
		info.setPort(transfer.getPort());
		
		postData(createUploadable(new File(transfer.getFileName())), info);
	}

	public void setUploadListener(UploadListener listener) {
		this.uploadListener = listener;
	}
	
	public void removeUploadListener() {
		this.uploadListener = null;
	}

	private String postData(MultiValueMap<String, Object> upload, HostInfo host)  {
		try {
			URI destination = new URI("http://" + host.getAddress() + ":" + host.getPort() + "/upload");
			UploadResult result = restTemplate.postForObject(destination, upload, UploadResult.class);
			notificationModel.notifyImageUploaded();
			return "Picture transfer complete: " + result.getResult();
		} catch (RestClientException e) {
			Log.e(getClass().getName(), Log.getStackTraceString(e));
		} catch (URISyntaxException e) {
		}
		File file = ((FileSystemResource) upload.get("file").get(0)).getFile();
		retryModel.uploadFailed(file, host);
		return "An error occured while uploading your image.";
	}
	
	private MultiValueMap<String, Object> createUploadable(File file) {
		MultiValueMap<String, Object> upload = new LinkedMultiValueMap<String, Object>();
		upload.add("file", new FileSystemResource(file));
		return upload;
	}

}
