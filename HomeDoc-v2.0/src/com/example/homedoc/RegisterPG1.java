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
import android.widget.TextView;

/**
 * 
 * @author John
 * The RegisterPG1 activity is page one of our registration page. This will take in
 * values such as name, address, city, state, and zip. Once a user has inputed these values, we will send them to
 * page 2 of our registration page.
 */

public class RegisterPG1 extends Activity {

	Context context = this;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Textviews from xml
		//do we really need these???
		TextView firstName = (TextView) findViewById(R.id.textViewFirstName);
		TextView lastName = (TextView) findViewById(R.id.textViewLastName);
		TextView address = (TextView) findViewById(R.id.textViewAdress);
		TextView city = (TextView) findViewById(R.id.textViewCity);
		TextView state = (TextView) findViewById(R.id.textViewState);
		TextView zip = (TextView) findViewById(R.id.textViewZip);
		
		
		//Edit texts from xml
		final EditText firNameEdit = (EditText) findViewById(R.id.editTextFirstName);
		final EditText lastNameEdit = (EditText) findViewById(R.id.editTextLastName);
		final EditText addrEdit = (EditText) findViewById(R.id.editTextAddr);
		final EditText cityEdit = (EditText) findViewById(R.id.editTextCity);
		final EditText stateEdit = (EditText) findViewById(R.id.editTextState);
		final EditText zipEdit = (EditText) findViewById(R.id.editTextZip);
		
		
		//intent from our final confirmation page
		//will only have items if user clicked edit button
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		if(extras!=null){
			String firName = extras.getString("firName");
			firNameEdit.setText(firName);
			String lasName = extras.getString("lasName");
			lastNameEdit.setText(lasName);
			String addr = extras.getString("addr");
			addrEdit.setText(addr);
			String cityIn = extras.getString("city");
			cityEdit.setText(cityIn);
			String stateIn = extras.getString("state");
			stateEdit.setText(stateIn);
		}
		
		//button to continue to next page
		Button continueNext = (Button) findViewById(R.id.buttonCont);
		continueNext.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent register2Start = new Intent(RegisterPG1.this, RegisterPG2.class);
				if(firNameEdit.getText().toString().trim().equals("")|| lastNameEdit.getText().toString().trim().equals("") 
						|| addrEdit.getText().toString().trim().equals("") || cityEdit.getText().toString().trim().equals("") ||
						stateEdit.getText().toString().trim().equals("") || zipEdit.getText().toString().trim().equals("")){
					alert();
				}else{
					

					register2Start.putExtra("firstName", firNameEdit.getText().toString());
					register2Start.putExtra("lastName", lastNameEdit.getText().toString());
					register2Start.putExtra("address", addrEdit.getText().toString());
					register2Start.putExtra("city", cityEdit.getText().toString());
					register2Start.putExtra("state", stateEdit.getText().toString());
					register2Start.putExtra("zip", zipEdit.getText().toString());
			    	RegisterPG1.this.startActivity(register2Start);	
				}
			}
		});
		
		//button to clear form, will erase all
		Button clear = (Button) findViewById(R.id.buttonClear);
		clear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				firNameEdit.setText("");
				lastNameEdit.setText("");
				addrEdit.setText("");	
				cityEdit.setText("");	
				stateEdit.setText("");	
				zipEdit.setText("");	
				
			}
		});
		
	}//end on create
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
