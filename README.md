Wagon
=====

#####A no fuss method of passing extras between Android Activities using annotations.


##Example

####Annotate which fields you'd like to put in the wagon:
```Java
  public class MainActivity extends Activity {
	@WoodBox(key = "theString")
	public String sTRING = "I'm a string";
	@WoodBox(key = "theList")
	public ArrayList<String> lIST;
	//...
}
```
####Pack your boxes in the wagon and start the next activity:
```Java
	private void startNextAcitivity() {
		Wagon<MainActivity> wagon = new Wagon<MainActivity>(this.getClass(), this);//this==MainActivity
		Intent intent = new Intent(getApplicationContext(), OtherActivity.class);
		wagon.pack(intent);
		startActivity(intent);
		finish();
	}
```
####Unpack your boxes from the wagon
```Java
public class OtherActivity extends Activity {
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
