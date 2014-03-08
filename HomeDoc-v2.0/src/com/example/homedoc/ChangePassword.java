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



import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Abraham
 * The purpose of the of this class is to allow user to reset their password.
 * As the user updates their password the application will connect to the database
 *
 */

public class ChangePassword extends Activity {

	Context context = this;
	private ProgressDialog pDialog;//Process dialog notifying the user or patient that their request is being processed
	String username;//The username that is needed to send the request
	String password;// The password to retrieve the old password
	EditText newPW ;//Edit text to set the new password
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		/*Extras that are from the previous class 
		 * 
		 * 
		 */
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if(extras != null){
			username = extras.getString("username");//Geting the username from edit profile
			 password = extras.getString("password");//Getting the password from edit profile
		}
		
		//Our edit texts
		final EditText oldPW = (EditText) findViewById(R.id.editTextOldPW);
		 newPW = (EditText) findViewById(R.id.editTextNewPW);
		final EditText newPWConfi = (EditText) findViewById(R.id.editTextConfirPW);
		oldPW.setText(password);
		
		//Our buttons
		Button newPWSave = (Button) findViewById(R.id.buttonSavePW);
		Button goHome = (Button) findViewById(R.id.buttonHome);
		
		//sends user back to main screen page
		goHome.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent register2Start = new Intent(ChangePassword.this, MainScreen.class);
				ChangePassword.this.startActivity(register2Start);				
			}
		});
		
		newPWSave.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String old = oldPW.getText().toString();
				String nPW = newPW.getText().toString();
				String nPWConfir = newPWConfi.getText().toString();
				
				//need to check that the old password is correct!!!
				if(!nPW.equals(nPWConfir)){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							context);
						// set title
						alertDialogBuilder.setTitle("Unmatching password");
						// set dialog message
						alertDialogBuilder
							.setMessage("The password must be the same as your new password")
							.setCancelable(true)
							.setPositiveButton("Close",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									dialog.cancel();
								}
							});
			 
							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder.create();
			 
							// show it
							alertDialog.show();
				}else{
					new updatePassword().execute();
				}
			
			}
			
			
		});
		
		
		
	}
	
	/*
	 * This class will use Asynchronous Task to perform the update password
	 */

    class updatePassword extends AsyncTask<String, String, String> {
    	
    	
    	
    	protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePassword.this);
            pDialog.setMessage("Updating..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    	
    	
    	 protected String doInBackground(String... args) {
    	
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://coolproject.x10.mx/updatepassword.php");

        try {
            // Add your data
            List<NameValuePair> params = new ArrayList<NameValuePair>();
           //parameters we are passing to the PHP script
            params.add(new BasicNameValuePair("userName", username));
            params.add(new BasicNameValuePair("password", newPW.getText().toString()));
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
                     "Password was updated successfully", Toast.LENGTH_LONG).show();
             pDialog.dismiss();
         }
		
		
		
    }
	
}
