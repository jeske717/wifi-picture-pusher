package org.jesko.picture.pusher.service;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.jesko.picture.pusher.beans.HostInfo;
import org.jesko.picture.pusher.beans.UploadResult;
import org.jesko.picture.pusher.host.HostModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PictureSuckerServiceModelTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private RestTemplate restTemplate;
	@Mock
	private UploadListener uploadListener;
	@Mock
	private RetryModel retryModel;
	@Mock
	private HostModel hostModel;
	@Mock
	private NotificationModel notificationModel;
	
	private PictureSuckerServiceModel testObject;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		when(restTemplate.postForObject(any(URI.class), anyObject(), any(Class.class))).thenReturn(new UploadResult());
		
		testObject = new PictureSuckerServiceModel(restTemplate, retryModel, hostModel, notificationModel);
	}
	
	@Test
	public void formMessageConverterAndJacksonMapperAreAddedToRestTemplate() throws Exception {
		verify(restTemplate.getMessageConverters()).add(isA(FormHttpMessageConverter.class));
		verify(restTemplate.getMessageConverters()).add(isA(MappingJackson2HttpMessageConverter.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void startUploadPostsFileToHostWithRestTemplate() throws Exception {
		HostInfo hostInfo = mock(HostInfo.class);
		when(hostInfo.getAddress()).thenReturn("address");
		when(hostInfo.getPort()).thenReturn(1111);
		when(hostModel.getSelectedHosts()).thenReturn(Collections.singleton(hostInfo));
		File file = mock(File.class);
		ArgumentCaptor<Object> requestCaptor = ArgumentCaptor.forClass(Object.class);
		
		testObject.startUpload(file);
		
		verify(restTemplate).postForObject(eq(new URI("http://address:1111/upload")), requestCaptor.capture(), eq(UploadResult.class));
		MultiValueMap<String, Object> request = (MultiValueMap<String, Object>) requestCaptor.getValue();
		assertSame(file, ((FileSystemResource)request.get("file").get(0)).getFile());
	}
	
	@Test
	public void startUploadPostsFileToMultipleHostsWithRestTemplate() throws Exception {
		HostInfo hostInfo1 = mock(HostInfo.class);
		when(hostInfo1.getAddress()).thenReturn("address");
		when(hostInfo1.getPort()).thenReturn(1111);
		HostInfo hostInfo2 = mock(HostInfo.class);
		when(hostInfo2.getAddress()).thenReturn("address2");
		when(hostInfo2.getPort()).thenReturn(1112);
		when(hostModel.getSelectedHosts()).thenReturn(new HashSet<HostInfo>(Arrays.asList(hostInfo1, hostInfo2)));
		File file = mock(File.class);
		
		testObject.startUpload(file);
		
		verify(restTemplate).postForObject(eq(new URI("http://address:1111/upload")), anyObject(), eq(UploadResult.class));
		verify(restTemplate).postForObject(eq(new URI("http://address2:1112/upload")), anyObject(), eq(UploadResult.class));
	}
	
	@Test
	public void startUploadPostsTransferToHostWithRestTemplate() throws Exception {
		Transfer transfer = mock(Transfer.class);
		when(transfer.getAddress()).thenReturn("address");
		when(transfer.getPort()).thenReturn(1111);
		when(transfer.getFileName()).thenReturn("/");
		
		testObject.startUpload(transfer);
		
		verify(restTemplate).postForObject(eq(new URI("http://address:1111/upload")), anyObject(), eq(UploadResult.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void startUploadNotifiesUploadListenerWhenDone() throws Exception {
		HostInfo hostInfo = mock(HostInfo.class);
		when(hostModel.getSelectedHosts()).thenReturn(Collections.singleton(hostInfo));
		File file = mock(File.class);
		testObject.setUploadListener(uploadListener);
		UploadResult result = mock(UploadResult.class);
		when(result.getResult()).thenReturn("the message");
		when(restTemplate.postForObject(any(URI.class), anyObject(), any(Class.class))).thenReturn(result);
		
		testObject.startUpload(file);
		
		verify(uploadListener).uploadCompleted("Picture transfer complete: the message");
	}
	
	@Test
	public void startUploadTellsNotificationModelThatImageHasBeenUploaded() throws Exception {
		HostInfo hostInfo = mock(HostInfo.class);
		when(hostModel.getSelectedHosts()).thenReturn(Collections.singleton(hostInfo));
		File file = mock(File.class);
		testObject.setUploadListener(uploadListener);
		
		testObject.startUpload(file);
		
		verify(notificationModel).notifyImageUploaded();
	}

}
