package org.jesko.picture.pusher.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.Context;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ImageNameCreatorTest {

	@Mock
	private Context context;
	@Mock
	private ImageNameModel imageNameModel;
	@Mock
	private UuidGenerator uuidGenerator;
	
	private ImageNameCreator testObject;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		testObject = new ImageNameCreator(context, imageNameModel, uuidGenerator);
	}
	
	@Test
	public void generateNextFileGeneratesRandomFileInExternalDirToSetOnModel() {
		when(context.getExternalFilesDir(null)).thenReturn(new File("/"));
		when(uuidGenerator.generate()).thenReturn("some random file name");
		
		testObject.generateNextFile();
		
		verify(imageNameModel).setCurrentFile(new File("/some random file name.jpg"));
	}
	
	@Test
	public void generateNextFileReturnsRandomJpgFileInExternalDir() throws Exception {
		when(context.getExternalFilesDir(null)).thenReturn(new File("/"));
		when(uuidGenerator.generate()).thenReturn("some random jpg");
		
		File result = testObject.generateNextFile();
		
		assertEquals(new File("/some random jpg.jpg"), result);
	}
	
	@Test
	public void testGetCurrentFileReturnsFileFromImageNameModel() throws Exception {
		when(imageNameModel.getCurrentFile()).thenReturn(new File("/weeeee"));
		
		File result = testObject.getCurrentFile();
		
		assertEquals(new File("/weeeee"), result);
	}

}
