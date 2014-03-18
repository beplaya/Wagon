package com.aj.wagon.examples;

import java.util.ArrayList;

public class CrateExample {

	public ArrayList<String> theList = new ArrayList<String>() {
		{
			add("listInsideCrate0");
			add("listInsideCrate1");
			add("listInsideCrate2");
		}
	};

	public String theString = "stringInsideCrate";
}
