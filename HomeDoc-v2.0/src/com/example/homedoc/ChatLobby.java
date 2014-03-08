/**
 * ChatLobby class which creates a lobby where users can join and chat.
 * author @Marianna
 * Adapted from www.gamooga.com 
 */

package com.example.homedoc;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.gamooga.client.ConnectCallback;
import com.gamooga.client.DisconnectCallback;
import com.gamooga.client.ErrorCallback;
import com.gamooga.client.GamoogaClient;
import com.gamooga.client.MessageCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChatLobby extends Activity {
	
	private GamoogaClient gc;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gc = new GamoogaClient();
        setContentView(R.layout.activity_chat_lobby);
        
        
		/**
		 * On connection success, we send a message of type mynick to the 
		 * server along with the nick as the identification of the user.
		 */
        gc.onconnect(new ConnectCallback() {
    		@Override
    		public void handle() {
    			runOnUiThread(new Runnable() {
    				@Override
    				public void run() {
    					TextView tv = (TextView)findViewById(R.id.chatData);
    					tv.setText(tv.getText() + "*** Connected\n");
    					int rnd = (int)(Math.random() * 100000);
    					try {
							gc.send("mynick", "user_"+rnd);
						} catch (JSONException e) {
							e.printStackTrace();
						}
    				}
    			});
    		}
        });
        
        
        /**
         * Since a userjoin message indicates a new user join, we add a message 
         * into the chatData TextView indicating it.
         */
        gc.onmessage("userjoin", new MessageCallback() {
			@Override
			public void handle(Object data) {
				final String nick = (String)data; 
				runOnUiThread(new Runnable() {
    				@Override
    				public void run() {
    					TextView tv = (TextView)findViewById(R.id.chatData);
    					tv.setText(tv.getText() + "*** "+nick+" joins\n");
    				}
    			});
			}
		});
        
        
        /**
         *  A userlist message indicates the server has sent us the list of online users right 
         *  after connect. We add a message to the chatData TextView with the nick of each connected
         *  user that he is online. The nicks are received as a dictionary in the data argument.
         */
        gc.onmessage("userlist", new MessageCallback() {
			@Override
			public void handle(Object data) {
				JSONObject jo = (JSONObject)data;

				final StringBuilder sb = new StringBuilder();
				JSONObject nickList;
				try {
					nickList = jo.getJSONObject("ol");
				} catch (JSONException e) {
					nickList = new JSONObject();
				}
				Iterator i = nickList.keys();
				while(i.hasNext()) {
					sb.append("*** "+(String)i.next()+" is online\n");
				}
				runOnUiThread(new Runnable() {
    				@Override
    				public void run() {
    					TextView tv = (TextView)findViewById(R.id.chatData);
    					tv.setText(tv.getText() + sb.toString());
    				}
    			});
			}
        });
    
        
        /**
         * We add a message to chatData TextView containing the chat message and 
         * the user sending it. We receive the user who sent the chat message and 
         * the chat message itself in the data argument which is a dictionary as 
         * sent from the server side.
         */
        gc.onmessage("chat", new MessageCallback() {
			@Override
			public void handle(Object data) {
				final JSONObject chat = (JSONObject)data;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
    					try {
    						TextView tv = (TextView)findViewById(R.id.chatData);
        					tv.setText(tv.getText() + chat.getString("f") + ": " + chat.getString("c")+"\n");
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
        
        
        /**
         * Callback registered with ondisconnect is called as soon as a user disconnects. 
         * We cleanup the user data and broadcast a message usergone with the nick of the disonnected user.
         */
        gc.onmessage("usergone", new MessageCallback() {
			@Override
			public void handle(Object data) {
				final String nick = (String)data; 
				runOnUiThread(new Runnable() {
    				@Override
    				public void run() {
    					TextView tv = (TextView)findViewById(R.id.chatData);
    					tv.setText(tv.getText() + "*** "+nick+" gone away\n");
    				}
    			});
			}
		});
        
        gc.ondisconnect(new DisconnectCallback() {
			@Override
			public void handle() {
				runOnUiThread(new Runnable() {
    				@Override
    				public void run() {
    					TextView tv = (TextView)findViewById(R.id.chatData);
    					tv.setText(tv.getText() + "*** disconnected\n");
    				}
    			});
			}
		});
        
        gc.onerror(new ErrorCallback() {
			@Override
			public void handle(final int errno) {
				runOnUiThread(new Runnable() {
    				@Override
    				public void run() {
    					TextView tv = (TextView)findViewById(R.id.chatData);
    					tv.setText(tv.getText() + "error: "+errno+"\n");
    				}
    			});
			}
		});
        
        gc.connectToRoom(496, "401fa3ca-b84a-11e2-a6b8-f23c91df4bc1");
    }
    
    /**
     * Method sends a message of type chat along with the chat message as the data to the server.
     * @param v 
     */
    public void sendChat(View v) {
    	EditText chatMsg = (EditText)findViewById(R.id.chatMsg);
    	try {
			gc.send("chat", chatMsg.getText());
			chatMsg.setText("");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
}
