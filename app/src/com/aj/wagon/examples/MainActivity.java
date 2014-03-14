package com.aj.wagon.examples;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
		final Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);

		((TextView) findViewById(id.tv)).setText("Main");

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				startNextActivity(wagon);
			}

			private void startNextActivity(final Wagon<MainActivity> wagon) {
				Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
				wagon.pack(intent);
				startActivity(intent);
				finish();
			}
		}, 3000);
	}
}
