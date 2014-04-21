package com.aj.wagon.sample;

import java.util.ArrayList;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aj.wagon.Crate;
import com.aj.wagon.Wagon;
import com.aj.wagon.WoodBox;
import com.aj.wagon.sample.R.id;

public class MainActivity extends Activity {

	public static final String PREFERENCES_NAME = "prefName";

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
			add("listItem0");
			add("listItem1");
			add("listItem2");
			add("listItem3");
		}
	};

	@WoodBox(key = "theString")
	public String sTRING = "I'm a string";

	@WoodBox(key = "aValue", preference = true)
	public String value;

	private Button btnSave;
	private Button btnLoad;
	private Button btnStartNext;
	private EditText etValue;
	private TextView tvTheFloat;
	private Wagon<MainActivity> wagon;
	private TextView tvList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		value = "default";
		setContentView(R.layout.activity_main);
		wagon = new Wagon<MainActivity>(this.getClass(), this);

		((TextView) findViewById(id.tv_title)).setText("Main Activity using Wagon v" + Wagon.VERSION);
		btnSave = (Button) findViewById(id.btn_save_prefs);
		btnLoad = (Button) findViewById(id.btn_load_prefs);
		btnStartNext = (Button) findViewById(id.btn_start_next);
		tvTheFloat = (TextView) findViewById(id.tv_float);
		tvList = (TextView) findViewById(id.tv_list);
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				save();
			}
		});
		btnLoad.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				load();
			}
		});
		btnStartNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startNextAcitivity();
			}
		});

		etValue = (EditText) findViewById(id.etValue);
		updateView();
		//
		PostData postData = new PostData();

		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		Wagon<PostData> w = new Wagon<PostData>(postData.getClass(), postData);
		w.pack(list);

		for (NameValuePair nameValuePair : list) {
			Log.e("", nameValuePair.getName() + " " + nameValuePair.getValue());
		}
	}

	private void updateView() {
		String listToString = "theList: [";
		for (int i = 0; i < lIST.size(); i++) {
			String string = lIST.get(i);
			listToString += i + ":(" + string + ")";
		}
		listToString += "]";
		tvList.setText(listToString);
		etValue.setText(value);
		tvTheFloat.setText("The float in the nested crate: " + crateExample.nestedCrate.theFloat);
	}

	private void load() {
		String msg = "";
		String oldValue = value;
		if (wagon.unpack(getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE))) {
			msg = "Loaded Preferences!";
		} else {
			msg = "Problem Loading!";
		}
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
		if (!oldValue.equals(value))
			etValue.setText(value);

		updateView();
	}

	private void save() {
		String msg = "";
		value = etValue.getText().toString();
		if (wagon.pack(getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE))) {
			msg = "Saved Preferences!";
		} else {
			msg = "Problem saving!";
		}
		value = "";// in order to prove value is loaded.

		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	private void startNextAcitivity() {
		final Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
		wagon.pack(intent);

		startActivity(intent);
		finish();
	}
}
