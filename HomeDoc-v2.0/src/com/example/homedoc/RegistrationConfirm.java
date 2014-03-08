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
import org.json.*;







import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author John
 * The registration confirm acitivity is where we take the info from registration
 * page 1 and registration page 2 and display it for the user in case they have made mistakes. 
 * If a user is ready to continue, there is a create account button.
 * If a user needs to edit, there is an edit button sending the user back to page 1.
 * If the user has made a mistake, there is a main menu button allowing the user to login
 */

public class RegistrationConfirm extends Activity {

	 private ProgressDialog pDialog;
	 String Fname;
	 String Lname;
	 String addr;
	 String city;
	 String state;
	 String zip; 
	 String email;
	 String userName;
	 String password;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_confirm);
		
		setTitle("Confirmation");
		
		//textviews for confirmation page of registration
				final TextView nameConfirm = (TextView) findViewById(R.id.textViewNameConfir);
				final TextView addrConfirm = (TextView) findViewById(R.id.textViewAddrConfir);
				final TextView cityConfirm = (TextView) findViewById(R.id.textViewCityConfir);
				final TextView stateConfirm = (TextView) findViewById(R.id.textViewStateConfir);
				TextView emailConfirm = (TextView) findViewById(R.id.textViewEmailConfir);
				TextView userConfirm = (TextView) findViewById(R.id.textViewUserNameConfir);
		
		
		//buttons from our registration confirmation page
		Button confirm = (Button) findViewById(R.id.buttonConfirmFinish);
		Button edit = (Button) findViewById(R.id.buttonEdit);
		Button returnBack = (Button) findViewById(R.id.buttonCancelConfirm);
		
		//extras from different intent
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		if(extras != null){
		 Fname = extras.getString("Fname");
		 Lname = extras.getString("Lname");
			
			nameConfirm.setText(Fname +" " +Lname);
		 addr = extras.getString("addr");
			addrConfirm.setText(addr);
		city = extras.getString("city");
			cityConfirm.setText(city);
		state = extras.getString("state");
			stateConfirm.setText(state);
		zip = extras.getString("zip");
			//zipConfirm.setText(zip);
		email = extras.getString("email");
			emailConfirm.setText(email);
		userName = extras.getString("userName");
	     password = extras.getString("password");
			userConfirm.setText(userName);
		}
		
		//edit on click listener
		edit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent editInfo = new Intent(RegistrationConfirm.this, RegisterPG1.class);
				String [] names = nameConfirm.getText().toString().split(" ");
				String firName = names[0];
				String lasName = names[1];
				editInfo.putExtra("firName", firName);
				editInfo.putExtra("lasName", lasName);
				editInfo.putExtra("addr", addrConfirm.getText().toString());;
				editInfo.putExtra("city", cityConfirm.getText().toString());;
				editInfo.putExtra("state", stateConfirm.getText().toString());;
				
		    	RegistrationConfirm.this.startActivity(editInfo);				
			}
		});

		
		//reuturn back on click listener
		returnBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent returnMain = new Intent(RegistrationConfirm.this, MainActivity.class);
		    	RegistrationConfirm.this.startActivity(returnMain);				
			}
		});
		
		confirm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new RegisterPatient().execute();//This will execute RegisterPatient
							
			}
		});
		
		
	}
	
	/*
	 * 
	 * Asynchronous Task will be utilized here in order to register the user
	 */
	
    class RegisterPatient extends AsyncTask<String, String, String> {
    	
    	
    	
    	protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistrationConfirm.this);
            pDialog.setMessage("Registering..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    	
    	
    	 protected String doInBackground(String... args) {
    	
    	 
    	
    	
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://coolproject.x10.mx/registerpatient.php");

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("FName", Fname));
            params.add(new BasicNameValuePair("LName", Lname));
            params.add(new BasicNameValuePair("Address", addr));
            params.add(new BasicNameValuePair("City", city));
            params.add(new BasicNameValuePair("State", state));
            params.add(new BasicNameValuePair("ZipCode", zip)); 
            params.add(new BasicNameValuePair("Email", email));
            params.add(new BasicNameValuePair("userName", userName));
            params.add(new BasicNameValuePair("password", password));
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
                     "Registration Successful", Toast.LENGTH_LONG).show();
             pDialog.dismiss();
         }
		
		
		
    }
	
}
