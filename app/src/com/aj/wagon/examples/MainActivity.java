package com.aj.wagon.examples;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aj.wagon.Crate;
import com.aj.wagon.R;
import com.aj.wagon.R.id;
import com.aj.wagon.Wagon;
import com.aj.wagon.WoodBox;

public class MainActivity extends Activity {

	// All fields in crates will be copied to
	// another instance in the next activity
	@Crate(key = "theCrate")
	public CrateExample crateExample = new CrateExample(new ArrayList<String>() {
		{
			add("listInsideCrate0");
			add("listInsideCrate1");
			add("listInsideCrate2");
		}
	}, "stringInsideCrate", 43, 77.5f, (long) 43214);

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

	@WoodBox(key = "aValue")
	public String value;

	private Button btnSave;
	private Button btnStartNext;
	private EditText etValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((TextView) findViewById(id.tv_title)).setText("Main");
		btnSave = (Button) findViewById(id.btn_save_prefs);
		btnStartNext = (Button) findViewById(id.btn_start_next);
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				save();
			}
		});
		btnStartNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNextAcitivity();
			}
		});

		etValue = (EditText) findViewById(id.etValue);
		//
		load();
	}

	private void load() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);
		String msg = "";
		if (wagon.unpack(getPreferences(MODE_PRIVATE))) {
			msg = "Loaded!";
		} else {
			msg = "Problem Loading!";
		}
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		etValue.setText(value);
	}

	private void save() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);
		String msg = "";
		if (wagon.pack(getPreferences(MODE_PRIVATE))) {
			msg = "Saved!";
		} else {
			msg = "Problem saving!";
		}
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	private void startNextAcitivity() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);
		final Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
		wagon.pack(intent);

		startActivity(intent);
		finish();
	}
}
