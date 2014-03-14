package com.aj.wagon.examples;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.aj.wagon.R;
import com.aj.wagon.R.id;
import com.aj.wagon.Wagon;
import com.aj.wagon.WoodBox;

public class MainActivity extends Activity {

	@WoodBox(key = "theList")
	public ArrayList<String> lIST = new ArrayList<String>() {
		{
			add("dasds0");
			add("dasds1");
			add("dasds2");
			add("dasds3");
		}
	};

	@WoodBox(key = "theString")
	public String sTRING = "I'm a string";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((TextView) findViewById(id.tv)).setText("Main");

		startNextAcitivity();
	}

	private void startNextAcitivity() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);
		final Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
		wagon.pack(intent);

		startActivity(intent);
		finish();
	}
}
