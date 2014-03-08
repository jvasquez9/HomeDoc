package com.example.homedoc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.homedoc.RegistrationConfirm.RegisterPatient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Abraham
 * The purpose of this class is to allow user to update their profile
 * user can edit their basic information
 * 
 *
 */

public class EditProfile extends Activity {
	 private ProgressDialog pDialog;
	 String firName;//Users first name
	 String lastName;//Users last name
	 String addr1;// user address
     String city1; //user city
	 String state1; // user city
	 String zip1; //user zip 
	 String email1; //user email
	 String username1;//user username
	 String password1;// user password
	 
    EditText name;//Edit text to change the users name
	EditText addr;//Edit text to change the users address
	EditText city;//Edit Text to change the users city 
	EditText state;//Edit text to change users state
	EditText zip; // Edit text to chage users zip
	EditText email; //
	EditText username;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		/*
		 * Inte will grab the extras from the main screen class
		 */
		Intent i1 = getIntent();
		Bundle extras1 = i1.getExtras();
		if(extras1 != null){
			 firName = extras1.getString("firstname");
			 lastName = extras1.getString("lastname");
			 addr1 = extras1.getString("address");
			 city1 = extras1.getString("city");
			 state1 = extras1.getString("state");
			 zip1 = extras1.getString("zip");
			 email1 = extras1.getString("email");
		     username1 = extras1.getString("username");
			 password1 = extras1.getString("password");
		
		}
		
		//Buttons
		Button changePW = (Button) findViewById(R.id.buttonChangePassword);
		Button save = (Button) findViewById(R.id.buttonSaveEdit);
		
		//Edit texts
		 name = (EditText) findViewById(R.id.editTextNameEdit);
		 addr = (EditText) findViewById(R.id.editTextAddrEdit);
		 city = (EditText) findViewById(R.id.autoCompleteTextViewCityEdit);
		 state = (EditText) findViewById(R.id.editTextStateEdit);
		 zip = (EditText) findViewById(R.id.editTextZipEdit);
		 email = (EditText) findViewById(R.id.editTextEmailEdit);
		 username = (EditText) findViewById(R.id.editTextUNEdit);
		
		name.setText(firName + " " + lastName);
		addr.setText(addr1);
		city.setText(city1);
		state.setText(state1);
		zip.setText(zip1);
		email.setText(email1);
		username.setText(username1);
		
		changePW.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent changePWStart = new Intent(EditProfile.this, ChangePassword.class);
				changePWStart.putExtra("username", username1);
				changePWStart.putExtra("password", password1);
				
		    	EditProfile.this.startActivity(changePWStart);				
			}
		});
		
		save.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//this will be where we save our items to the database should the user 
				//change anything, still nee
				String [] names = name.getText().toString().split(" ");
				 firName = names[0];
				 lastName = names[1];
				addr1= addr.getText().toString();
				city1= city.getText().toString();
				state1= state.getText().toString();
				zip1= zip.getText().toString();
				//email1= email.getText().toString();
				 
				 
				 
				 new updatePatient().execute();//executes updatePatient
				
			}
		});
	}
	
	/*
	 * This class will use Asynchronous Task to perform the update patient basic information
	 */
	
	 class updatePatient extends AsyncTask<String, String, String> {
	    	
	    	
	    	
	    	protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(EditProfile.this);
	            pDialog.setMessage("Updating user information..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	    	
	    	
	    	 protected String doInBackground(String... args) {
	    	
	    	 
	    	
	    	
	        // Create a new HttpClient and Post Header
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://coolproject.x10.mx/updatepatient.php");

	        try {
	            // Add your data
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("FName", firName));
	            params.add(new BasicNameValuePair("LName", lastName));
	            params.add(new BasicNameValuePair("Address", addr1));
	            params.add(new BasicNameValuePair("City", city1));
	            params.add(new BasicNameValuePair("State", state1));
	            params.add(new BasicNameValuePair("ZipCode", zip1)); 
	            params.add(new BasicNameValuePair("Email", email1));
	            params.add(new BasicNameValuePair("userName", username1));
	            httppost.setEntity(new UrlEncodedFormEntity(params));

	            // Execute HTTP Post Request
	            HttpResponse response = httpclient.execute(httppost);
	            
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        }
	        	return null;
	    }
	    	 
	    	 protected void onPostExecute(String file_url) {
	             // dismiss the dialog once done
	    		 Toast.makeText(getApplicationContext(), 
	                     "Your Profile was updated successfully", Toast.LENGTH_LONG).show();
	             pDialog.dismiss();
	         }
			
			
			
	    }
	
}
