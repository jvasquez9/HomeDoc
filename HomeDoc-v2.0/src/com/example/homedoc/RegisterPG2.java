package com.example.homedoc;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author John Vasquez
 *
 * The registerPG2 class is the second page of our registration page and will allow 
 * the user to finish their registration for the app. 
 * In the activity, we are taking in values from four edit texts, email, user name, password, and password confirm
 * These variables will be saved to our database
 */

public class RegisterPG2 extends Activity {

	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_pg2);
		setTitle("Register Page 2");
		
		//Our edit texts from activity register 2
		final EditText email = (EditText) findViewById(R.id.editTextEmail);
		final EditText userName = (EditText) findViewById(R.id.editTextUserName);
		final EditText password = (EditText) findViewById(R.id.editTextPassword);
		final EditText passwordConfirm = (EditText) findViewById(R.id.editTextConPW);
		
		//buttons from our registration 2
		Button next = (Button) findViewById(R.id.buttonNext);
		Button cancel = (Button) findViewById(R.id.buttonCancel);
		
		
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		final String firName = extras.getString("firstName");
		final String lastName = extras.getString("lastName");
		final String addr = extras.getString("address");
		final String city = extras.getString("city");
		final String state = extras.getString("state");
		 final String zip = extras.getString("zip");
		
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!password.getText().toString().equals(passwordConfirm.getText().toString())  )
				{
					
					Toast.makeText(getApplicationContext(), 
		            "Your passwords did not match", Toast.LENGTH_LONG).show();
				}
				
				else{
					if(email.getText().toString().trim().equals("")|| userName.getText().toString().trim().equals("") ||
							password.getText().toString().trim().equals("") || passwordConfirm.getText().toString().trim().equals("")){
						alert();
					}else{
						Intent confirm = new Intent(RegisterPG2.this, RegistrationConfirm.class);
						confirm.putExtra("email", email.getText().toString());
						confirm.putExtra("userName", userName.getText().toString());
						confirm.putExtra("Fname" , firName);
						confirm.putExtra("Lname" , lastName);
						confirm.putExtra("addr", addr);
						confirm.putExtra("city", city);
						confirm.putExtra("state", state);
						confirm.putExtra("zip", zip);
						confirm.putExtra("password", password.getText().toString());
				    	RegisterPG2.this.startActivity(confirm);
					}
				
				
				}
				
			}
		});
		
		
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent back2main = new Intent(RegisterPG2.this, MainActivity.class);
		    	RegisterPG2.this.startActivity(back2main);				
			}
		});
	}

	/**
	 * creates an alert to tell user there is missing info
	 */
	private void alert(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
			// set title
			alertDialogBuilder.setTitle("Error");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You must fill out all fields before continuing")
				.setCancelable(false)
				.setPositiveButton("Close",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				// show it
				alertDialog.show();
	}//end alert all

}
