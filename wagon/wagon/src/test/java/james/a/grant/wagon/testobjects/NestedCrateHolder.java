package james.a.grant.wagon.testobjects;


import james.a.grant.wagon.Crate;

public class NestedCrateHolder {

    @Crate(key = "crateWithCrateNested")
    public james.a.grant.wagon.testobjects.CrateHolder crateHolder;

    public NestedCrateHolder() {
        crateHolder = new james.a.grant.wagon.testobjects.CrateHolder();
    }

    public NestedCrateHolder(String s, int i, float f, double d, long l) {
        crateHolder = new james.a.grant.wagon.testobjects.CrateHolder(s, i, f, d, l);
    }

    public TestCrate getNestedCrate() {
        return crateHolder.testCrate;
    }
}
