package com.example.homedoc;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
 * @author John
 * The doc orders activity is designed to give a user the a list of 
 * orders the Doctor has given the paitient. A spinner will contain a list
 * of orders with a given date, and once one it is selected, the orders will display
 * on screen for a user.
 *
 */

public class DocOrders extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doc_orders);
		
		//our button to head home
		Button home = (Button) findViewById(R.id.buttonReturnFromOrders);
		home.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent retHome = new Intent(DocOrders.this, MainScreen.class);
		    	DocOrders.this.startActivity(retHome);				
			}
		});
		
		
		//the spinner containing our doctors orders
		//this is meant to be filled by our database once it has
		//been completed but we are still awating as of 5/7/13
		Spinner orders = (Spinner) findViewById(R.id.spinnerOrders);
		
		//our values are hardcoded for demonstration purposes
		final String [] array_spinner = new String[6];
		array_spinner[0]="";
		array_spinner[1]="5/02/13";
		array_spinner[2]="4/01/13";
		array_spinner[3]="1/13/13";
		array_spinner[4]="12/05/12";
		array_spinner[5]="11/03/12";
		
		final String [] array_message = new String[6];
		array_message[0] = "";
		array_message[1] =  "Your sugar level is very low, I want you to try and eat a more balanced diet. By doing so, you will feel better and breather easier. See you next week!";
		array_message[2] = "You are eating too much pizza, your heart rate is too high. Take your meds and we will meet again soon.";
		array_message[3] = "Good job on the exercises, keep it up and we will meet soon";
		array_message[4] = "I hope we will begin putting you on meds soon as your health is not very good";
		array_message[5] = "Welcome, we will have you first check up soon";
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.row, R.id.data, array_spinner);
		orders.setAdapter(adapter);
		orders.setSelection(0);
		
		
		//our TextView
		final TextView date1 = (TextView) findViewById(R.id.textViewOrder1);
		final TextView message1 = (TextView) findViewById(R.id.textViewOrder1Message);
		
		orders.setOnItemSelectedListener(new OnItemSelectedListener() {
		    
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	int index = parentView.getSelectedItemPosition();
		    	
		    	for(int i = 0; i < array_spinner.length;i++){
		    		if(index == 1){
		    			date1.setText(array_spinner[index]);
		    			message1.setText(array_message[index]);
		    		}if(index == 2){
		    			date1.setText(array_spinner[index]);
		    			message1.setText(array_message[index]);
		    		}if(index == 3){
		    			date1.setText(array_spinner[index]);
		    			message1.setText(array_message[index]);
		    		}if(index == 4){
		    			date1.setText(array_spinner[index]);
		    			message1.setText(array_message[index]);
		    		}if(index == 5){
		    			date1.setText(array_spinner[index]);
		    			message1.setText(array_message[index]);
		    		}
		    	}
		    	
		    }

		    /**
		     * This class is a default class, we are not using this for our application
		     */
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // 
		    }

			
		});
		
	}
}
	
