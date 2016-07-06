package james.a.grant.wagon;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import james.a.grant.wagon.testobjects.CrateHolder;
import james.a.grant.wagon.testobjects.NestedCrateHolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(TestRunner.class)
public class WagonTests {
    @WoodBox(key = "key", preference = false)
    public String testBox = "testBox";

    @Before
    public void setUp() {

    }

    @Test
    public void itPacksAllTheBoxes() {
        james.a.grant.wagon.testobjects.Boxes boxes = new james.a.grant.wagon.testobjects.Boxes("s", 1, 2, 3, 4);
        Wagon<james.a.grant.wagon.testobjects.Boxes> wagon = new Wagon<>(boxes.getClass(), boxes);
        Intent intent = new Intent();
        wagon.pack(intent);
        //
        assertThat(intent.getExtras().getString("s"), is("s"));
        assertThat(intent.getExtras().getInt("i"), is(1));
        assertThat(intent.getExtras().getFloat("f"), is(2f));
        assertThat(intent.getExtras().getDouble("d"), is(3d));
        assertThat(intent.getExtras().getLong("l"), is(4l));
    }

    @Test
    public void itUnpacksAllTheBoxes() {
        james.a.grant.wagon.testobjects.Boxes boxes = new james.a.grant.wagon.testobjects.Boxes("s", 1, 2, 3, 4);
        Wagon<james.a.grant.wagon.testobjects.Boxes> wagon = new Wagon<james.a.grant.wagon.testobjects.Boxes>(boxes.getClass(), boxes);
        Intent intent = new Intent();
        wagon.pack(intent);
        //
        boxes = new james.a.grant.wagon.testobjects.Boxes();
        assertThat(boxes.s, nullValue());
        Wagon<james.a.grant.wagon.testobjects.Boxes> wagon2 = new Wagon<james.a.grant.wagon.testobjects.Boxes>(boxes.getClass(), boxes);
        wagon2.unpack(intent);
        assertThat(boxes.s, is("s"));
        assertThat(boxes.i, is(1));
        assertThat(boxes.f, is(2f));
        assertThat(boxes.d, is(3d));
        assertThat(boxes.l, is(4l));
    }

    @Test
    public void itPacksTheCrate() {
        CrateHolder crateHolder = new CrateHolder("s", 1, 2, 3, 4);
        Wagon<CrateHolder> wagon = new Wagon<CrateHolder>(crateHolder.getClass(), crateHolder);
        Intent intent = new Intent();
        wagon.pack(intent);
        //
        crateHolder = new CrateHolder();
        assertThat(crateHolder.testCrate.s, nullValue());
        Wagon<CrateHolder> wagon2 = new Wagon<CrateHolder>(crateHolder.getClass(), crateHolder);
        wagon2.unpack(intent);
        assertThat(crateHolder.testCrate.s, is("s"));
        assertThat(crateHolder.testCrate.i, is(1));
        assertThat(crateHolder.testCrate.f, is(2f));
        assertThat(crateHolder.testCrate.d, is(3d));
        assertThat(crateHolder.testCrate.l, is(4l));
    }

    @Test
    public void itPacksTheNestedCrate() {
        NestedCrateHolder nestedCrateHolder = new NestedCrateHolder("s", 1, 2, 3, 4);
        Wagon<NestedCrateHolder> wagon = new Wagon<NestedCrateHolder>(nestedCrateHolder.getClass(), nestedCrateHolder);
        Intent intent = new Intent();
        wagon.pack(intent);
        //
        nestedCrateHolder = new NestedCrateHolder();
        assertThat(nestedCrateHolder.getNestedCrate().s, nullValue());
        Wagon<NestedCrateHolder> wagon2 = new Wagon<NestedCrateHolder>(nestedCrateHolder.getClass(), nestedCrateHolder);
        wagon2.unpack(intent);
        assertThat(nestedCrateHolder.getNestedCrate().s, is("s"));
        assertThat(nestedCrateHolder.getNestedCrate().i, is(1));
        assertThat(nestedCrateHolder.getNestedCrate().f, is(2f));
        assertThat(nestedCrateHolder.getNestedCrate().d, is(3d));
        assertThat(nestedCrateHolder.getNestedCrate().l, is(4l));
    }

    @Test
    public void itDPacksAndUnpacksNonPreferences() {
        Wagon<WagonTests> wagon = new Wagon<WagonTests>(this.getClass(), this);
        Intent intent = new Intent();
        wagon.pack(intent);
        assertThat(intent.getExtras().getString("key"), is("testBox"));
        //
        testBox = null;
        Wagon<WagonTests> wagon2 = new Wagon<WagonTests>(this.getClass(), this);
        wagon2.unpack(intent);
        assertThat(intent.getExtras().getString("key"), is("testBox"));
    }
}
