package com.aj.wagon.sample;

import java.util.ArrayList;

import com.aj.wagon.Crate;
import com.aj.wagon.WoodBox;

public class PostData {

	@WoodBox(key = "ps")
	public String ps = "hahaha";

	@Crate(key = "psCrate")
	public CrateExample crate = new CrateExample(new ArrayList<String>() {
		{
			add("listInsideCrate0");
			add("listInsideCrate1");
			add("listInsideCrate2");
		}
	}, "stringInsideCrate", 43, 77.5f, (long) 43214);
}
