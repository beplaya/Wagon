package com.aj.wagon.examples;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.aj.wagon.R;
import com.aj.wagon.R.id;
import com.aj.wagon.Wagon;
import com.aj.wagon.WoodBox;

public class OtherActivity extends Activity {
	@WoodBox(key = "theList")
	public ArrayList<String> lIST = new ArrayList<String>();

	@WoodBox(key = "theString")
	public String sTRING = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Wagon<OtherActivity> wagon = new Wagon<OtherActivity>(this.getClass(), this);
		wagon.unpack(getIntent());

		((TextView) findViewById(id.tv)).setText("Other");

		Log.i("WAGON", "~~~~~~~~~~~START~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~BOXES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~BOXES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~!BOXES~~~~~~~~~~~~~~~");
		for (String string : lIST) {
			Log.i("theList", string);
		}
		Log.i("theString", sTRING);
		Log.i("WAGON", "~~~~~~~~~~~!BOXES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~BOXES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~BOXES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~END~~~~~~~~~~~~~~~");

	}
}
