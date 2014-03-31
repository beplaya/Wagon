package com.aj.wagon.examples;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.aj.wagon.Crate;
import com.aj.wagon.R;
import com.aj.wagon.R.id;
import com.aj.wagon.Wagon;
import com.aj.wagon.WoodBox;

public class OtherActivity extends Activity {

	@Crate(key = "theCrate")
	public CrateExample crateExample = new CrateExample();

	@WoodBox(key = "theList")
	public ArrayList<String> lIST = new ArrayList<String>();

	@WoodBox(key = "theString")
	public String sTRING = "";

	private TextView tvList;
	private TextView tvTheFloat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		unpack();

		log();

		((TextView) findViewById(id.other_tv_title)).setText("Other");

		tvList = (TextView) findViewById(id.other_tv_list);
		tvTheFloat = (TextView) findViewById(id.other_tv_float);

		tvTheFloat.setText("The float in the nested crate: " + crateExample.nestedCrate.theFloat);

		String listToString = "theList: [";
		for (String string : lIST) {
			listToString += "(" + string + ")";
		}
		listToString += "]";
		tvList.setText(listToString);
	}

	private void unpack() {
		final Wagon<OtherActivity> wagon = new Wagon<OtherActivity>(this.getClass(), this);
		if (!wagon.unpack(getIntent())) {
			Toast.makeText(this, "Error unpacking wagon!", Toast.LENGTH_LONG).show();
		}
	}

	private void log() {
		Log.i("WAGON", "~~~~~~~~~~~START~~~~~~~~~~~~~~~");
		Log.i("WAGON", "                           ~");
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
		Log.i("WAGON", "                           ~");
		Log.i("WAGON", "~~~~~~~~~~~CRATES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~CRATES~~~~~~~~~~~~~~~");
		crateExample.print();

		Log.i("WAGON", "~~~~~~~~~~~CRATES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "~~~~~~~~~~~CRATES~~~~~~~~~~~~~~~");
		Log.i("WAGON", "                           ~");
		Log.i("WAGON", "~~~~~~~~~~~END~~~~~~~~~~~~~~~");
	}
}
