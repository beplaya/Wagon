Wagon
=====

#####A no fuss method of passing extras between Android Activities using annotations.


##Example

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
	private void startNextAcitivity() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);//this==MainActivity
		Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
		wagon.pack(intent);
		startActivity(intent);
	}
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
}
```

####CrateExample class used above
public class CrateExample {

	public ArrayList<String> theListInCrate = new ArrayList<String>() {
		{
			add("listInsideCrate0");
			add("listInsideCrate1");
			add("listInsideCrate2");
		}
	};

	public String theStringInCrate = "stringInsideCrate";

	public void print() {
		for (String string : theListInCrate) {
			Log.i("CrateExampletheList", string);
		}
		Log.i("CrateExampletheString", theStringInCrate);
	}
}

