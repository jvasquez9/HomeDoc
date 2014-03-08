package com.example.homedoc;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainScreen extends Activity {

	int valueNum1 = 42;
	int valueNum2 = 20;
	int valueNum3 = 30;
	String firName;
	String lastName;
	String addr;
	String city;
	String state;
	String zip;
	String email;
	String username;
	String password;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		
		
		
		//our buttons
		Button orders = (Button) findViewById(R.id.buttonDocOrders);
		Button pers = (Button) findViewById(R.id.buttonDocPres);
		Button edit = (Button) findViewById(R.id.buttonEditProfile);
		Button map = (Button) findViewById(R.id.buttonFindHos);
		Button logout = (Button) findViewById(R.id.buttonLogOut);
		Button chat = (Button) findViewById(R.id.buttonDocChat);
		
		Intent intent = getIntent().setClass(this, MainActivity.class);
		Bundle extras = intent.getExtras();
		if(extras != null){
			/*
			 * After a successful login the users information is passed and we are obtaining the
			 * this getting the extras passed
			 * 
			 */
				firName = extras.getString("firstname1");
				 lastName = extras.getString("lastname1");
				 addr = extras.getString("address1");
				 city = extras.getString("city1");
				 state = extras.getString("state1");
				 zip = extras.getString("zip1");
				 email = extras.getString("email1");
				 username = extras.getString("username1");
				 password = extras.getString("password1");
				
		}
		
		
		//this intent is suppose to just grab extras from the 
		//prescriptions class but this is not working
		Intent intent2 = getIntent().setClass(this, Prescriptions.class);
		Bundle extras2 = intent2.getExtras();
		
		if(extras2!=null){	
			
			
			valueNum1 = extras.getInt("value1");
			valueNum2 = extras.getInt("value2");
			valueNum3 = extras.getInt("value3");
		}
		
		
		pers.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//send the values of our prescritions from main activity (either the original or altered int) and send it back
				Intent persStart = new Intent(MainScreen.this, Prescriptions.class);
				
				persStart.putExtra("value1", valueNum1);
				persStart.putExtra("value2", valueNum2);
				persStart.putExtra("value3", valueNum3);
		    	MainScreen.this.startActivity(persStart);		
			}
		});
		
		//starts the doctors orders activity
		orders.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent ordersStart = new Intent(MainScreen.this, DocOrders.class);
		    	MainScreen.this.startActivity(ordersStart);		
			}
		});
		
		//starts the edit profile activity
		edit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent editStart = new Intent(MainScreen.this, EditProfile.class);
		    	
				
		    	editStart.putExtra("firstname", firName);
				editStart.putExtra("lastname", lastName);
				editStart.putExtra("address", addr);
				editStart.putExtra("city", city);
				editStart.putExtra("state", state);
				editStart.putExtra("zip", zip);
				editStart.putExtra("email", email);
				editStart.putExtra("username", username);
				editStart.putExtra("password", password);
				MainScreen.this.startActivity(editStart);
			}
		});
		
		
		//this starts our map activity
		map.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent mapStart = new Intent(MainScreen.this, CheckMap.class);
		    	MainScreen.this.startActivity(mapStart);		
			}
		});
		
		//logs the user out, sends the back to the main activity
		logout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent logout = new Intent(MainScreen.this, MainActivity.class);
				Toast.makeText(getBaseContext(), "Goodbye", Toast.LENGTH_LONG).show();
		    	MainScreen.this.startActivity(logout);		
			}
		});
		
		//this starts the chat function of our app
		chat.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent logout = new Intent(MainScreen.this, ChatLobby.class);
		    	MainScreen.this.startActivity(logout);		
			}
		});
		
	}

}
