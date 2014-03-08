package com.example.homedoc;



import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class MainActivity extends Activity {
	final String url = "http://coolproject.x10.mx/login.php";//url of the server
	AQuery aq;//creating a new AQuery
	EditText nameEntry;//Login name
	EditText passwordEntry;//Login password

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		aq = new AQuery(this);

		//text views for our main menu
		TextView welcome = (TextView) findViewById(R.id.textViewWelcome);
		TextView name = (TextView) findViewById(R.id.textViewUN);
		TextView password = (TextView) findViewById(R.id.textViewPW);
		
		//edit texts for our main menu
		 nameEntry = (EditText) findViewById(R.id.editTextUN);
		passwordEntry = (EditText) findViewById(R.id.editTextPW);
		
		//button to login 
		Button login = (Button) findViewById(R.id.buttonLogin);
		Button register = (Button) findViewById(R.id.buttonReg);
		register.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent registerStart = new Intent(MainActivity.this, RegisterPG1.class);
		    	MainActivity.this.startActivity(registerStart);				
			}
		});
		
    	
    	
    	aq.id(R.id.buttonLogin).clicked(this, "login");//when the Login button is clicked it executes "login"
		
	}//end on create
	
	
	
	/*
	 * This method allows the user to send information to PHP there PHP script will authenticate the user
	 */
	public void login(View v) {
		/*
		 * parameters to send php selects the username and password and sends back a response
		 */
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("username", aq.id(R.id.editTextUN).getText().toString());
		param.put("password", aq.id(R.id.editTextPW).getText().toString());
		
		aq.ajax(url, param, JSONObject.class, this, "loginTask");//executing ajax will call login Task	
	}
	
	
	/*
	 * 
	 * Login task will have a JSonObject that will read the response 
	 * 
	 */
	public void loginTask(String url, JSONObject json, AjaxStatus status) {
		try {
			//Getting response from the PHP script 
			String name1 = nameEntry.getText().toString();
			String pass1 = passwordEntry.getText().toString();
			
			String name = json.getString("username");
			String pass = json.getString("password");
			String fname = json.getString("firstname");
			String lname = json.getString("lastname");
			String address = json.getString("address");
			String city = json.getString("city");
			String state = json.getString("state");
			String zip = json.getString("zip");
			String email = json.getString("email");
			
			/*
			 * 
			 * Authenticating the user
			 */
			
			if(name1.equals(name)&& pass1.equals(pass)){
				Intent logMain = new Intent(MainActivity.this, MainScreen.class);
				/*
				 * If a successfull login we will gather all the information from the user and 
				 * pass it to the another class using extras and intent
				 */
				logMain.putExtra("firstname1", fname);
				logMain.putExtra("lastname1", lname);
				logMain.putExtra("address1", address);
				logMain.putExtra("city1", city);
				logMain.putExtra("state1", state);
				logMain.putExtra("zip1", zip);
				logMain.putExtra("email1", email);
				logMain.putExtra("username1", name);
				logMain.putExtra("password1", pass);
				MainActivity.this.startActivity(logMain);
				
			}
			
			} catch (JSONException e){
			e.printStackTrace();
			Log.d("login", "failed to login");
			Toast.makeText(getApplicationContext(),
					"Your Credentials did not match",
					Toast.LENGTH_LONG).show();
		}
	}
	
}//end main activity
