package com.aj.wagon.examples;

import java.util.ArrayList;

import android.util.Log;

public class CrateExample {

	public ArrayList<String> theListInCrate;

	public String theStringInCrate;
	public int numberInt;

	public CrateExample() {
		this(new ArrayList<String>(), "", 0);
	}

	public CrateExample(ArrayList<String> l, String s, int number) {
		this.theListInCrate = l;
		this.theStringInCrate = s;
		this.numberInt = number;
	}

	public void print() {
		Log.i("", "" + numberInt);
		for (String string : theListInCrate) {
			Log.i("CrateExampletheList", string);
		}
		Log.i("CrateExampletheString", theStringInCrate);
	}
}
