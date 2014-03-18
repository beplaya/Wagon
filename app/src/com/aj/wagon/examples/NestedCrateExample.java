package com.aj.wagon.examples;

import android.util.Log;

public class NestedCrateExample {

	public float theFloat;
	public long theLong;

	public NestedCrateExample() {
	}

	public NestedCrateExample(float f, long l) {
		this.theFloat = f;
		this.theLong = l;
	}

	public void print() {
		Log.i("nested f", "" + theFloat);
		Log.i("nested l", "" + theLong);
	}
}
