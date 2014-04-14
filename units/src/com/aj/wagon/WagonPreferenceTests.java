package com.aj.wagon;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.aj.wagon.testobjects.Boxes;

@RunWith(TestRunner.class)
public class WagonPreferenceTests {

	@Mock
	private SharedPreferences mockPreferences;
	@Mock
	private Editor mockEditor;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(mockPreferences.edit()).thenReturn(mockEditor);
	}

	@Test
	public void itPacksAllTheBoxes() {
		Boxes boxes = new Boxes("s", 1, 2, 3, 4);
		Wagon<Boxes> wagon = new Wagon<Boxes>(boxes.getClass(), boxes);
		wagon.pack(mockPreferences);
		verify(mockEditor).putString("s", "s");
		verify(mockEditor).putInt("i", 1);
		verify(mockEditor).putFloat("f", 2);
		verify(mockEditor).putString("d", PreferenceCollector.KEY_DOUBLE + PreferenceCollector.DELIM + 3.0d);
		verify(mockEditor).putLong("l", 4);

		wagon.unpack(mockPreferences);
		verify(mockPreferences).getString("s", null);
		verify(mockPreferences).getInt("i", 0);
		verify(mockPreferences).getFloat("f", 0);
		verify(mockPreferences).getString("d", null);
		verify(mockPreferences).getLong("l", 0);
	}
}
