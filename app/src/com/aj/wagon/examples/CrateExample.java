package com.aj.wagon.examples;

import java.util.ArrayList;

import android.util.Log;

public class CrateExample {

	public ArrayList<String> theListInCrate = new ArrayList<String>() {
		{
			add("listInsideCrate0");
			add("listInsideCrate1");
			add("listInsideCrate2");
		}
	};

	public String theStringInCrate = "stringInsideCrate";

	public void print() {
		for (String string : theListInCrate) {
			Log.i("CrateExampletheList", string);
		}
		Log.i("CrateExampletheString", theStringInCrate);
	}
}
