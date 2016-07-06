#Wagon

#####Makes passing extras and saving/loading preferences clean and easy via annotations.

![](https://raw.githubusercontent.com/beplaya/Wagon/master/wagon_100.png)
##How To Use:
Put the wagon_x.xx.jar in the Android 'libs' folder of your project.  If 'libs' doesn't exist, create it at the same level as 'src'.

##Examples: ([See wiki] (https://github.com/beplaya/Wagon/wiki))

##Gradle
```compile 'james.a.grant:wagon:1.1.0'```

### Bintray ([Wagon] (https://bintray.com/beplaya/maven/wagon))

####Annotate which fields you'd like to put in the wagon:
```Java

  	public class MainActivity extends Activity {
	// All fields in crates will be copied to
	// another instance in the next activity
	@Crate(key = "theCrate")
	public CrateExample crateExample = new CrateExample();
	
	@WoodBox(key = "theString")
	public String sTRING = "I'm a string";
	@WoodBox(key = "theList")
	public ArrayList<String> lIST;
	//...
}
```
####Pack your wagon and start the next activity:
```Java
	//...
	private void startNextAcitivity() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);//this==MainActivity
		Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
		wagon.pack(intent);
		startActivity(intent);
	}
	//...
```
####Unpack your wagon:
```Java
public class OtherActivity extends Activity {

	//The crate from the last activity
	//will be copied into this crate instance
	//with the same field values
	@Crate(key = "theCrate")
	public CrateExample crateExample = new CrateExample();

	@WoodBox(key = "theList")
	public ArrayList<String> lIST = new ArrayList<String>();
	@WoodBox(key = "theString")
	public String sTRING = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Wagon<OtherActivity> wagon = new Wagon<OtherActivity>(this.getClass(), this);
		wagon.unpack(getIntent());
	}
	//...
}
```

####CrateExample class used above
```Java
public class CrateExample {

	public ArrayList<String> theListInCrate;

	public String theStringInCrate;
	public int numberInt;

	public CrateExample() {
		this(new ArrayList<String>(), "", 0);
	}

	public CrateExample(ArrayList<String> l, String s, int number) {
		this.theListInCrate = l;
		this.theStringInCrate = s;
		this.numberInt = number;
	}
	//...

}
```

####Nesting crates
#####You may nest crates within other crates without limit.  Simply annotate each Object field in the class as @Crate.
#####There is no reason to use a WoodBox within a Crate.  Any field within a Crate is already considered a WoodBox.
#####E.G.
```Java
	public class MainActivity extends Activity {
	@WoodBox(key = "aBox")
	public void float value = 42;

	@Crate(key = "aCrate")
	public CrateExample crateExample;
	//...
```
```java
public class CrateExample {

	//No need to annotate as WoodBox.  Already considered one.
	public void int number = 77;
	//No need to annotate as WoodBox.  Already considered one.
	public ArrayList<String> theListInCrate;

	@Crate(key = "aNestedCrate")
	public NestedCrateExample nestedCrate;
	//...
}
```
```Java
public class NestedCrateExample {

	//No need to annotate as WoodBox.  Already considered one.
	public float theFloat;
	//No need to annotate as WoodBox.  Already considered one.
	public long theLong;

	public NestedCrateExample() {
	}

	public NestedCrateExample(float f, long l) {
		this.theFloat = f;
		this.theLong = l;
	}
	//...
}
```

####SharedPreferences 
#####Just like extras, Wagon can pass annotated fields to SharedPreferences:
```Java
	...
	@Crate(key = "myPreferenceCrate", preference = true)
	public CrateExample crate;
	@WoodBox(key = "myPreferenceString", preference = true)
	public String value;
	...
	wagon.pack(getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE));
	...
	wagon.unpack(getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE));
	...
```
