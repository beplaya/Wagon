package james.a.grant.wagon.testobjects;


import james.a.grant.wagon.Crate;

public class CrateHolder {

    @Crate(key = "crateKey", preference = true)
    public TestCrate testCrate;

    public CrateHolder() {
        testCrate = new TestCrate();
    }

    public CrateHolder(String s, int i, float f, double d, long l) {
        testCrate = new TestCrate(s, i, f, d, l);
    }
}
