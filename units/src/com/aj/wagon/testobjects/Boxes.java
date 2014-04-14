package com.aj.wagon.testobjects;

import com.aj.wagon.WoodBox;

public class Boxes {

	@WoodBox(key = "s", preference = true)
	public String s;
	@WoodBox(key = "i", preference = true)
	public int i;
	@WoodBox(key = "f", preference = true)
	public float f;
	@WoodBox(key = "d", preference = true)
	public double d;
	@WoodBox(key = "l", preference = true)
	public long l;

	public Boxes() {
		this.s = null;
		this.i = -1;
		this.f = -1;
		this.d = -1;
		this.l = -1;
	}

	public Boxes(String s, int i, float f, double d, long l) {
		this.s = s;
		this.i = i;
		this.f = f;
		this.d = d;
		this.l = l;
	}

}
