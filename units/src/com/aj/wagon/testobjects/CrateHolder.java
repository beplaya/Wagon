package com.aj.wagon.testobjects;

import com.aj.wagon.Crate;

public class CrateHolder {

	@Crate(key = "crateKey")
	public TestCrate testCrate;

	public CrateHolder() {
		testCrate = new TestCrate();
	}

	public CrateHolder(String s, int i, float f, double d, long l) {
		testCrate = new TestCrate(s, i, f, d, l);
	}
}
