package com.aj.wagon;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;

import com.aj.wagon.testobjects.Boxes;

@RunWith(TestRunner.class)
public class WagonTests {

	@Before
	public void setUp() {

	}

	@Test
	public void itPacksAllTheBoxes() {
		Boxes boxes = new Boxes("s", 1, 2, 3, 4);
		Wagon<Boxes> wagon = new Wagon<Boxes>(boxes.getClass(), boxes);
		Intent intent = new Intent();
		wagon.pack(intent);
		//
		assertThat(intent.getExtras().getString("s"), is("s"));
		assertThat(intent.getExtras().getInt("i"), is(1));
		assertThat(intent.getExtras().getFloat("f"), is(2f));
		assertThat(intent.getExtras().getDouble("d"), is(3d));
		assertThat(intent.getExtras().getLong("l"), is(4l));
	}

	@Test
	public void itUnpacksAllTheBoxes() {
		Boxes boxes = new Boxes("s", 1, 2, 3, 4);
		Wagon<Boxes> wagon = new Wagon<Boxes>(boxes.getClass(), boxes);
		Intent intent = new Intent();
		wagon.pack(intent);
		//
		boxes = new Boxes();
		assertThat(boxes.s, nullValue());
		Wagon<Boxes> wagon2 = new Wagon<Boxes>(boxes.getClass(), boxes);
		wagon2.unpack(intent);
		assertThat(boxes.s, is("s"));
		assertThat(boxes.i, is(1));
		assertThat(boxes.f, is(2f));
		assertThat(boxes.d, is(3d));
		assertThat(boxes.l, is(4l));
	}

}
