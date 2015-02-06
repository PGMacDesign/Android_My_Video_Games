package com.pgmacdesign.myvideogames.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
The database class. It will hold all of the data retrieved from the website that the user chose from
the list.
 */
public class TheDatabase {

	//Helper object
	DatabaseHelper helper;

	private SQLiteDatabase database;

	//Constructor
	public TheDatabase(Context context){
		helper = new DatabaseHelper(context);
	}

	//Returns a long to determine if the insert was successful
	public long insertData(List<String> newData){
		//Creates an object of the SQLiteDatabase itself and then opens / creates a writeable database
		SQLiteDatabase db = helper.getWritableDatabase(); //This returns a database object

		//Create a content values object to help put data in
		ContentValues contentValues = new ContentValues();

		//Put data. Parameters are Key, Value
		//Put the values into the content values so that it can update
		contentValues.put(helper.COLUMN_GAME_ID,  newData.get(0));
		contentValues.put(helper.COLUMN_ALIASES,  newData.get(1));
		contentValues.put(helper.COLUMN_DECK,  newData.get(2));
		contentValues.put(helper.COLUMN_ICON_URL,  newData.get(3));
		contentValues.put(helper.COLUMN_MEDIUM_URL,  newData.get(4));

		contentValues.put(helper.COLUMN_SCREEN_URL,  newData.get(5));
		contentValues.put(helper.COLUMN_SMALL_URL,  newData.get(6));
		contentValues.put(helper.COLUMN_SUPER_URL,  newData.get(7));
		contentValues.put(helper.COLUMN_THUMB_URL,  newData.get(8));
		contentValues.put(helper.COLUMN_TINY_URL,  newData.get(9));

		contentValues.put(helper.COLUMN_NAME,  newData.get(10));
		contentValues.put(helper.COLUMN_ORIGINAL_RELEASE_DATE,  newData.get(11));
		contentValues.put(helper.COLUMN_PLATFORM_NAME,  newData.get(12));
		contentValues.put(helper.COLUMN_PLATFORM_ABBREVIATION,  newData.get(13));

		//Put the data into the database itself
		long result = db.insert(helper.TABLE_NAME, null, contentValues);

		return result;

	}

	//Returns ALL of the data in the database via via a list for the listView
	public List<String> getAllTableData(){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.COLUMN_GAME_ID, helper.COLUMN_DECK,
				helper.COLUMN_THUMB_URL, helper.COLUMN_NAME,
		};

		//Query the database. Third param is the search params, null returns ALL
		//This returns a cursor object
		cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null); //May need to add more Null arguments

		//A List to hold the pulled data
		List<String> pulled_data = new ArrayList<>();

		//While the cursor can move to the next (while there are more rows)
		while(cursor.moveToNext()){

			//Integer is retrieving column number where the name is
			int index0 = cursor.getColumnIndex(DatabaseHelper.COLUMN_GAME_ID);
			//Get the String from the column Name
			String str0 = cursor.getString(index0);

			int index2 = cursor.getColumnIndex(DatabaseHelper.COLUMN_DECK);
			String str2 = cursor.getString(index2);

			int index8 = cursor.getColumnIndex(DatabaseHelper.COLUMN_THUMB_URL);
			String str8 = cursor.getString(index8);

			int index10 = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
			String str10 = cursor.getString(index10);

			pulled_data.add(str0);
			pulled_data.add(str2);
			pulled_data.add(str8);
			pulled_data.add(str10);
		}

		return pulled_data;
	}

	//This returns the row with the game ID being passed in
	public List<String> getRow(String game_id){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.COLUMN_GAME_ID, helper.COLUMN_ALIASES, helper.COLUMN_DECK,
				helper.COLUMN_ICON_URL, helper.COLUMN_MEDIUM_URL, helper.COLUMN_SCREEN_URL,
				helper.COLUMN_SMALL_URL, helper.COLUMN_SUPER_URL, helper.COLUMN_THUMB_URL,
				helper.COLUMN_TINY_URL, helper.COLUMN_NAME, helper.COLUMN_ORIGINAL_RELEASE_DATE,
				helper.COLUMN_PLATFORM_NAME, helper.COLUMN_PLATFORM_ABBREVIATION
		};

		//Query the database. Third param are the search params. This returns a cursor object
		cursor = db.query(DatabaseHelper.TABLE_NAME, columns,
		helper.COLUMN_GAME_ID + " = '" + game_id + "'", null, null, null, null);

		//A List to hold the pulled data
		List<String> pulled_data = new ArrayList<>();

		//While the cursor can move to the next (while there are more rows)
		while(cursor.moveToNext()){

			//Integer is retrieving column number where the name is
			int index0 = cursor.getColumnIndex(DatabaseHelper.COLUMN_GAME_ID);
			//Get the String from the column Name
			String str0 = cursor.getString(index0);
			int index1 = cursor.getColumnIndex(DatabaseHelper.COLUMN_ALIASES);
			String str1 = cursor.getString(index1);
			int index2 = cursor.getColumnIndex(DatabaseHelper.COLUMN_DECK);
			String str2 = cursor.getString(index2);
			int index3 = cursor.getColumnIndex(DatabaseHelper.COLUMN_ICON_URL);
			String str3 = cursor.getString(index3);
			int index4 = cursor.getColumnIndex(DatabaseHelper.COLUMN_MEDIUM_URL);
			String str4 = cursor.getString(index4);
			int index5 = cursor.getColumnIndex(DatabaseHelper.COLUMN_SCREEN_URL);
			String str5 = cursor.getString(index5);
			int index6 = cursor.getColumnIndex(DatabaseHelper.COLUMN_SMALL_URL);
			String str6 = cursor.getString(index6);
			int index7 = cursor.getColumnIndex(DatabaseHelper.COLUMN_SUPER_URL);
			String str7 = cursor.getString(index7);
			int index8 = cursor.getColumnIndex(DatabaseHelper.COLUMN_THUMB_URL);
			String str8 = cursor.getString(index8);
			int index9 = cursor.getColumnIndex(DatabaseHelper.COLUMN_TINY_URL);
			String str9 = cursor.getString(index9);
			int index10 = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
			String str10 = cursor.getString(index10);
			int index11 = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORIGINAL_RELEASE_DATE);
			String str11 = cursor.getString(index11);
			int index12 = cursor.getColumnIndex(DatabaseHelper.COLUMN_PLATFORM_NAME);
			String str12 = cursor.getString(index12);
			int index13 = cursor.getColumnIndex(DatabaseHelper.COLUMN_PLATFORM_ABBREVIATION);
			String str13 = cursor.getString(index13);

			pulled_data.add(str0);
			pulled_data.add(str1);
			pulled_data.add(str2);
			pulled_data.add(str3);
			pulled_data.add(str4);
			pulled_data.add(str5);
			pulled_data.add(str6);
			pulled_data.add(str7);
			pulled_data.add(str8);
			pulled_data.add(str9);
			pulled_data.add(str10);
			pulled_data.add(str11);
			pulled_data.add(str12);
			pulled_data.add(str13);

		}

		return pulled_data;
	}

	//Updates a name in the database. Leaving this out for now as there is no update option
	public int updateRow(String widget_id, List<String> newData){
			int count = 1;
			return count;
	}

	//Deletes a row in the database by the game_id is being passed in
	public int deleteRow(String game_id){
			//Creating an SQL database by referencing the adapter,
			//which references the helper object, which opens the database
			SQLiteDatabase db = helper.getWritableDatabase();

			//The last parameter in update needs a string array, not a string, so creating the array here
			String[] whereArgs = {game_id};

			//Delete. Follows the command: DELETE * FROM TABLE WHERE NAME = ""
			int count = db.delete(helper.TABLE_NAME, helper.COLUMN_GAME_ID + "=?", whereArgs);

			return count;
			}

	//Opens the database
	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	//Closes the database object, helps reduce memory leaks
	public void close() {
		helper.close();
	}

	/////////////////////////////////////////////////////////////////////////////////
	//This class creates an object which extends to the database for commands
	//It is nested to encapsulate it
	static class DatabaseHelper extends SQLiteOpenHelper {

		//Standard variables for the database functions
		private static final String DATABASE_NAME = "videogamesdb";
		private static final String TABLE_NAME = "videogamestable";
		private static final int DATBASE_VERSION = 1;
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

		//Columns to store data

		// Column with the game_id, IE 13053
		public static final String COLUMN_GAME_ID = "game_id";
		// Stored as an String, aliases for the game, IE FF7, FFVII
		public static final String COLUMN_ALIASES = "aliases";
		// Stored as a string, a short description of the game
		public static final String COLUMN_DECK = "deck";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_ICON_URL = "icon_url";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_MEDIUM_URL = "medium_url";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_SCREEN_URL = "screen_url";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_SMALL_URL = "small_url";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_SUPER_URL = "super_url";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_THUMB_URL = "thumb_url";
		// Stored as an string, one of the image URLs
		public static final String COLUMN_TINY_URL = "tiny_url";
		// Stored as an string, the name of the game (IE Final Fantasy VII)
		public static final String COLUMN_NAME = "name";
		// Stored as an string, the original release date (IE 1997-01-31 00:00:00)
		public static final String COLUMN_ORIGINAL_RELEASE_DATE = "original_release_date";
		// Stored as a String, the platform name (IE Playstation)
		public static final String COLUMN_PLATFORM_NAME = "platform_name";
		// Stored as a String, the platform name abbreviation (IE PS1)
		public static final String COLUMN_PLATFORM_ABBREVIATION = "platform_abbreviation";

		private Context context; //In case we need context

		//Create table statement//
		private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_GAME_ID + " VARCHAR(255),"
				+ COLUMN_ALIASES + " VARCHAR(255),"
				+ COLUMN_DECK + " VARCHAR(255),"
				+ COLUMN_ICON_URL + " VARCHAR(255),"
				+ COLUMN_MEDIUM_URL + " VARCHAR(255),"
				+ COLUMN_SCREEN_URL + " VARCHAR(255),"
				+ COLUMN_SMALL_URL + " VARCHAR(255),"
				+ COLUMN_SUPER_URL + " VARCHAR(255),"
				+ COLUMN_THUMB_URL + " VARCHAR(255),"
				+ COLUMN_TINY_URL + " VARCHAR(255),"
				+ COLUMN_NAME + " VARCHAR(255),"
				+ COLUMN_ORIGINAL_RELEASE_DATE + " VARCHAR(255),"
				+ COLUMN_PLATFORM_NAME + " VARCHAR(255),"
				+ COLUMN_PLATFORM_ABBREVIATION + " VARCHAR(255)"
				+");";

		//Constructor
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATBASE_VERSION);
			this.context = context;
		}

		//This is called when the database is called for the first time
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Log.d("Database ", "Has been created");
			} catch (SQLException e) {
				Log.d("Database ", "Was NOT created");
				e.printStackTrace();
			}
		}

		//This is called when the database schema is changed (IE new columns or tables or deleting tables)
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_TABLE);
				Log.d("Database ", "Has been Dropped");
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
				Log.d("Database ", " Issue with onUpgrade");
			}
		}
	}
}
