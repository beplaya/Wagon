package com.aj.wagon.testobjects;

import com.aj.wagon.Crate;

public class NestedCrateHolder {

	@Crate(key = "crateWithCrateNested")
	public CrateHolder crateHolder;

	public NestedCrateHolder() {
		crateHolder = new CrateHolder();
	}

	public NestedCrateHolder(String s, int i, float f, double d, long l) {
		crateHolder = new CrateHolder(s, i, f, d, l);
	}

	public TestCrate getNestedCrate() {
		return crateHolder.testCrate;
	}
}
