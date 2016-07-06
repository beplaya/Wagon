package james.a.grant.wagon.testobjects;

import james.a.grant.wagon.WoodBox;

public class TestCrate {

    public String s;
    public int i;
    public float f;
    public double d;
    @WoodBox(key = "l", preference = false)
    public long l;

    public TestCrate() {
        this.s = null;
        this.i = -1;
        this.f = -1;
        this.d = -1;
        this.l = -1;
    }

    public TestCrate(String s, int i, float f, double d, long l) {
        this.s = s;
        this.i = i;
        this.f = f;
        this.d = d;
        this.l = l;
    }
}
