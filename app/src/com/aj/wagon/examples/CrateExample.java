package com.aj.wagon.examples;

import java.util.ArrayList;

import android.util.Log;

import com.aj.wagon.Crate;

public class CrateExample {

	public ArrayList<String> theListInCrate;

	@Crate(key = "theNestedCrate")
	public NestedCrateExample nestedCrate;
	public String theStringInCrate;
	public int numberInt;

	public CrateExample() {
		this(new ArrayList<String>(), "", 0, 0, 0);
	}

	public CrateExample(ArrayList<String> list, String s, int number, float f, long l) {
		this.theListInCrate = list;
		this.theStringInCrate = s;
		this.numberInt = number;
		this.nestedCrate = new NestedCrateExample(f, l);
	}

	public void print() {
		Log.i("", "" + numberInt);
		for (String string : theListInCrate) {
			Log.i("CrateExampletheList", string);
		}
		Log.i("CrateExampletheString", theStringInCrate);
		nestedCrate.print();
	}
}
