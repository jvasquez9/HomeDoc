package com.example.homedoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * The SQLite database class. 
 * @author Alla Dove
 * @author Marianna Pena
 */

public class DatabaseAdapter {
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DESC = "desc";
	public static final String KEY_ADDRESS = "adr";
	public static final String KEY_LAT = "lat";
	public static final String KEY_LONG = "long";
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "HW5DB";
	private static final String DATABASE_TABLE = "locations";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE ="create table locations (_id integer primary key, desc text, adr text, lat text, long text);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private int _id=0;
	
	/**
	 * Contrustor for the database.
	 * @param ctx context
	 */
	public DatabaseAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	/**
	 * The database helper class. 
	 * @author Alla Dove
	 * @author Marianna Pena
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS locations");
			onCreate(db);
		}
	}
	
	/**
	 * The method is used to open the database.
	 * @return
	 * @throws SQLException
	 */
	public DatabaseAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * The method is used to close the database.
	 */
	public void close()
	{
		DBHelper.close();
	}
	
	/**
	 * The method is used to insert a row of values into a database.
	 * @param desc to-do item description
	 * @param lat latitude of to-do item's location
	 * @param lng longitude of to-do item's location
	 * @return -1 if unsuccessful
	 */
	public long insertValues(String desc, String lat, String lng)
	{
		ContentValues values = new ContentValues();
		try{
		Cursor c=db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID}, null, null, null, null, null, null);
		c.moveToLast();
		_id=Integer.parseInt(c.getString(0))+1;
		}
		catch(CursorIndexOutOfBoundsException e)
		{
			_id=0;
		}
		values.put(KEY_ROWID, ""+_id);
		values.put(KEY_DESC, desc);
		values.put(KEY_LAT, lat);
		values.put(KEY_LONG, lng);
		return db.insert(DATABASE_TABLE, "", values);
	}
	
	/**
	 * The method is used to remove a row of values from a database.
	 * @param index the index number of the row to be removed
	 * @return true if successful
	 */
	public boolean removeValues(int index)
	{
		return db.delete(DATABASE_TABLE, KEY_ROWID + " = " + index, null) > 0;
		//Doesn't like this statement and crashes
		//db.delete(DATABASE_TABLE, "KEY_DESC=? AND KEY_LAT=? AND KEY_LONG=?", new String[] { desc, lat, lng });
	}
	
	/**
	 * The method is used to get all values from the database.
	 * @return a cursor which points to the first row of elements in the database.
	 */
	public Cursor getAll()
	{
		Cursor mCursor=db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DESC, KEY_LAT, KEY_LONG}, null, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
}
