package com.example.homedoc;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * 
 * @author John
 * The Prescriptions class is designed to work with a patients 
 * Dr. in displaying the current prescriptions the user is taking
 * and the current amount that they have.
 * We are pre setting the values until we connect this to the database
 *
 */

public class Prescriptions extends Activity {

	final Context context = this;
	int valueNum1 = 20;
	int valueNum2 = 32;
	int valueNum3 = 40;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescriptions);
		setTitle("Perscriptions");
		
		
		//the values of all of our number pickers
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if(extras != null){
			valueNum1 = extras.getInt("value1");
			valueNum2 = extras.getInt("value2");
			valueNum3 = extras.getInt("value3");
		}else{
			 
			valueNum1 = 20;
			valueNum2 = 42;
			valueNum3 = 30;
		}
		
		
		
		//our textviews for the perscription amount
		TextView pers1Name = (TextView) findViewById(R.id.textViewPerscrip1);
		TextView pers2Name = (TextView) findViewById(R.id.textViewPerscrip2);
		TextView pers3Name = (TextView) findViewById(R.id.textViewPerscrip3);
		
		//our perscrition buttons
		ImageButton pers1 = (ImageButton) findViewById(R.id.imageButtonPerscrip1);
		ImageButton pers2 = (ImageButton) findViewById(R.id.imageButtonPerscript2);
		ImageButton pers3 = (ImageButton) findViewById(R.id.imageButtonPerscrip3);
		
		//Number pickers
		//numbers are messed up because of naming in xml, will fix later, john
		final NumberPicker numPick1 = (NumberPicker) findViewById(R.id.numberPickerPersc2);
		final NumberPicker numPick2 = (NumberPicker) findViewById(R.id.numberPickerPersc3);
		final NumberPicker numPick3 = (NumberPicker) findViewById(R.id.numberPickerPersc1);
		
		//our one button in the perscription button
		Button returnToMain = (Button) findViewById(R.id.buttonReturnFromPers);
		
		//our number picks max value
		numPick1.setMaxValue(valueNum1);
		numPick2.setMaxValue(valueNum2);
		numPick3.setMaxValue(valueNum3);
		
		//setting the value of our number pickers
		numPick1.setValue(valueNum1);
		numPick2.setValue(valueNum2);
		numPick3.setValue(valueNum3);
		
		//number picks wrap selector
		numPick1.setWrapSelectorWheel(false);
		numPick2.setWrapSelectorWheel(false);
		numPick3.setWrapSelectorWheel(false);
		
		if(numPick1.getValue() == 0 && numPick2.getValue() == 0 && numPick3.getValue() == 0){
			alertAll();
		}
		if(numPick1.getValue() <= 5 && numPick1.getValue() > 0){
			alertPers(pers1Name.getText().toString());
		}if(numPick2.getValue() <= 5 && numPick2.getValue() > 0){
			alertPers(pers2Name.getText().toString());
		}if(numPick3.getValue() <= 5 && numPick3.getValue() > 0){
			alertPers(pers3Name.getText().toString());
		}
		
		pers1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
		 
					// set title
					alertDialogBuilder.setTitle("Tylenol");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("An over the counter pain reducing medicine. Best if taking only 2 per 4 hours")
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
			}
		});
		
		pers2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
		 
					// set title
					alertDialogBuilder.setTitle("Advil");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("An over the counter drug used to reduce pain and infalmation. Best if taken 2 per 4 hours")
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
			}
		});
		
		pers3.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);
		 
					// set title
					alertDialogBuilder.setTitle("Tramadol");
					
					SpannableString s = new SpannableString("http://www.drugs.com/tramadol.html");
					Linkify.addLinks(s, Linkify.WEB_URLS);
					// set dialog message
					alertDialogBuilder
						.setMessage("A pain reliever used to treat moderate to severe pain. For More info click: " + s)
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
			}
		});

		returnToMain.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				int sendNum1 = numPick1.getValue();
				int sendNum2 = numPick2.getValue();
				int sendNum3 = numPick3.getValue();
				
				//sending the numbers of each prescription so it will not change back to the original amount
				Intent goToMain = new Intent(Prescriptions.this, MainScreen.class);
				goToMain.putExtra("value1", sendNum1);
				goToMain.putExtra("value2", sendNum2);
				goToMain.putExtra("value3", sendNum3);
		    	Prescriptions.this.startActivity(goToMain);				
				
			}
		});
		
		
	}
	
	/**
	 * 
	 * @param persName: The string that we want to display or which prescription is being displayed
	 */
	public void alertPers(String persName){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
			// set title
			alertDialogBuilder.setTitle("Perscription Count");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Your Perscription count for " + persName +" is low, contact your doctor to see if you need a refill")
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
	}//end alert Pers
	
	
	/**
	 * This method will be used when we display all the presecriptions alerts
	 */
	private void alertAll(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
			// set title
			alertDialogBuilder.setTitle("Perscription Count");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You have finished all of your prescriptions, contact you doctor for a refill")
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
