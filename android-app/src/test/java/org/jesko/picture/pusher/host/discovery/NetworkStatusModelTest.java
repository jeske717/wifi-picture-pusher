package org.jesko.picture.pusher.host.discovery;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NetworkStatusModelTest {

	@Mock
	private ConnectivityManager connectivityManager;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private WifiManager wifiManager;
	
	private NetworkStatusModel testObject;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		testObject = new NetworkStatusModel(connectivityManager, wifiManager);
	}
	
	@Test
	public void getCurrentConnectedSsidReturnsNullWhenNotConnectedToWifi() throws Exception {
		NetworkInfo networkInfo = mock(NetworkInfo.class);
		when(networkInfo.isConnected()).thenReturn(false);
		when(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).thenReturn(networkInfo);
		
		String result = testObject.getCurrentConnectedSsid();
		
		assertNull(result);
	}
	
	@Test
	public void getCurrentConnectedSsidReturnsSsidFromWifiWhenConnected() throws Exception {
		NetworkInfo networkInfo = mock(NetworkInfo.class);
		when(networkInfo.isConnected()).thenReturn(true);
		when(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).thenReturn(networkInfo);
		when(wifiManager.getConnectionInfo().getSSID()).thenReturn("the ssid");
		
		String result = testObject.getCurrentConnectedSsid();
		
		assertEquals("the ssid", result);
	}
}
