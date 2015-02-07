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

	//This class checks if the database is empty. If it is, it adds a few bogus values
	public void checkIfDBIsEmpty(Context context){

		//List to determine size of returned data
		List<String> temp_list = new ArrayList<>();
		//Fill the list
		temp_list = getAllGameIDs();

		Log.d("Number of Rows in Database : ", Integer.toString(temp_list.size()));

		//If it is empty, add values, else, move onward
		if (temp_list.size() == 0){
			//Nothing in the database, need to add some fictitious files.

			List<String> list1 = new ArrayList<>();
			//Fill list 1
			list1.add("13053");
			list1.add("FFVII");
			list1.add("A young man's quest to defeat a corrupt corporation he once served and exact revenge upon the man who wronged him, " +
					"uncovering dark secrets about his past along the way, in the most celebrated console RPG of all time. " +
					"It popularized the RPG genre and was a killer app for the PlayStation console.");
			list1.add("http://static.giantbomb.com/uploads/square_avatar/8/87790/1814630-box_ff7.png");
			list1.add("http://static.giantbomb.com/uploads/scale_medium/8/87790/1814630-box_ff7.png");
			list1.add("http://static.giantbomb.com/uploads/scale_medium/8/87790/1814630-box_ff7.png");
			list1.add("http://static.giantbomb.com/uploads/scale_medium/8/87790/1814630-box_ff7.png");
			list1.add("http://static.giantbomb.com/uploads/scale_medium/8/87790/1814630-box_ff7.png");
			list1.add("http://static.giantbomb.com/uploads/scale_avatar/8/87790/1814630-box_ff7.png");
			list1.add("http://static.giantbomb.com/uploads/scale_medium/8/87790/1814630-box_ff7.png");
			list1.add("Final Fantasy 7");
			list1.add("1997-01-31");
			list1.add("Playstation");
			list1.add("PS1");
			list1.add("true");
			list1.add("4");
			insertData(list1);

			List<String> list2 = new ArrayList<>();
			//Fill list2
			list2.add("10299");
			list2.add("SMB3");
			list2.add("Super Mario Bros. 3 sends Mario on a whole new adventure across diverse worlds and sporting strange new suits and abilities.");
			list2.add("http://static.giantbomb.com/uploads/square_avatar/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2362272-nes_supermariobros3_4.jpg");
			list2.add("Super Mario Bros. 3");
			list2.add("1988-10-23");
			list2.add("Nintendo Entertainment System");
			list2.add("NES");
			list2.add("false");
			list2.add("2");
			insertData(list2);

			List<String> list3 = new ArrayList<>();
			//Fill List 3
			list3.add("10276");
			list3.add("Zelda 3");
			list3.add("The third installment in the Zelda series makes a return to the top-down 2D gameplay found in the " +
					"first game. This time around, Link needs to travel between the light and dark world in order to set " +
					"things straight in the kingdom of Hyrule.");
			list3.add("http://static.giantbomb.com/uploads/square_avatar/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("http://static.giantbomb.com/uploads/scale_medium/9/93770/2363926-snes_thelegendofzeldaalinktothepast.jpg");
			list3.add("The Legend of Zelda: A Link to the Past");
			list3.add("1991-11-21");
			list3.add("Super Nintendo Entertainment System");
			list3.add("SNES");
			list3.add("false");
			list3.add("3");
			insertData(list3);

		}


	}

	//Returns long to determine if the update was successful. Updates an entire row.
	//Pass in the game ID and a list of all the values to update. Note, values muse be
	//in order matching contentValues list here.
	public long updateRow(String gameID, List <String> newData){

		//Creating an SQL database by referencing the adapter,
		//which references the helper object, which opens the database
		SQLiteDatabase db = helper.getWritableDatabase();

		//Content values for passing in data
		ContentValues contentValues = new ContentValues();

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

		contentValues.put(helper.COLUMN_PLAYED_CHECKBOX,  newData.get(14));
		contentValues.put(helper.COLUMN_RATING,  newData.get(15));

		//The last parameter in update needs a string array, not a string, so creating the array here
		String[] whereArgs = {gameID};

		//Update. Follows the command: UPDATE TABLE SET NAME = "" WHERE NAME = ?
		//Returns a count of how many rows were updated
		int count = db.update(helper.TABLE_NAME, contentValues, helper.COLUMN_GAME_ID + " =?", whereArgs);
		return count;

	}

	//Returns a long to determine if the insert was successful, updates the COLUMN_PLAYED_CHECKBOX
	//Pass in the game ID and a string to update the played checkbox
	public long updatePlayedCheckbox(String gameID, String value){

		//Creating an SQL database by referencing the adapter,
		//which references the helper object, which opens the database
		SQLiteDatabase db = helper.getWritableDatabase();

		//Content values for passing in data
		ContentValues contentValues = new ContentValues();
		contentValues.put(helper.COLUMN_PLAYED_CHECKBOX,  value);

		//The last parameter in update needs a string array, not a string, so creating the array here
		String[] whereArgs = {gameID};

		//Update. Follows the command: UPDATE TABLE SET NAME = "" WHERE NAME = ?
		//Returns a count of how many rows were updated
		int count = db.update(helper.TABLE_NAME, contentValues, helper.COLUMN_GAME_ID + " =?", whereArgs);
		return count;

	}

	//Returns a long to determine if the insert was successful, updates the COLUMN_RATING
	//Pass in the game ID and a float to update the rating.
	public long updateRating(String gameID, float value){

		//Creating an SQL database by referencing the adapter,
		//which references the helper object, which opens the database
		SQLiteDatabase db = helper.getWritableDatabase();

		//Content values for passing in data
		ContentValues contentValues = new ContentValues();

		//Put the values into the content values so that it can update
		contentValues.put(helper.COLUMN_RATING,  Float.toString(value));

		//The last parameter in update needs a string array, not a string, so creating the array here
		String[] whereArgs = {gameID};

		//Update. Follows the command: UPDATE TABLE SET NAME = "" WHERE NAME = ?
		//Returns a count of how many rows were updated
		int count = db.update(helper.TABLE_NAME, contentValues, helper.COLUMN_GAME_ID + " =?", whereArgs);
		return count;
	}


	//Returns a long to determine if the insert was successful
	//Pass a list of all the values to update. Note, values muse be
	//in order matching contentValues list here.
	public long insertData(List<String> newData){
		//Creates an object of the SQLiteDatabase itself and then opens / creates a writeable database
		SQLiteDatabase db = helper.getWritableDatabase(); //This returns a database object

		//Create a content values object to help put data in
		ContentValues contentValues = new ContentValues();

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

		contentValues.put(helper.COLUMN_PLAYED_CHECKBOX,  newData.get(14));
		contentValues.put(helper.COLUMN_RATING,  newData.get(15));

		//Put the data into the database itself
		long result = db.insert(helper.TABLE_NAME, null, contentValues);

		return result;

	}

	//Returns ALL of the game IDs from the database (One column)
	public List<String> getAllGameIDs(){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.COLUMN_GAME_ID};

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
			pulled_data.add(str0);
		}
		//List of all the game IDs
		return pulled_data;
	}

	//Returns some of the data in the database via a list for the listView.
	public List<String> getSomeTableData(){
		//First make the object
		SQLiteDatabase db = helper.getWritableDatabase();

		//Cursor object for traversing the results of the database
		Cursor cursor;

		//Create a string array of data
		String[] columns = {helper.COLUMN_GAME_ID, helper.COLUMN_DECK,
				helper.COLUMN_THUMB_URL, helper.COLUMN_NAME,
				helper.COLUMN_PLAYED_CHECKBOX, helper.COLUMN_RATING
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

			int index14 = cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYED_CHECKBOX);
			String str14 = cursor.getString(index14);

			int index15 = cursor.getColumnIndex(DatabaseHelper.COLUMN_RATING);
			String str15 = cursor.getString(index15);

			pulled_data.add(str0);
			pulled_data.add(str2);
			pulled_data.add(str8);
			pulled_data.add(str10);
			pulled_data.add(str14);
			pulled_data.add(str15);
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
				helper.COLUMN_PLATFORM_NAME, helper.COLUMN_PLATFORM_ABBREVIATION,
				helper.COLUMN_PLAYED_CHECKBOX, helper.COLUMN_RATING
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
			int index14 = cursor.getColumnIndex(DatabaseHelper.COLUMN_PLAYED_CHECKBOX);
			String str14 = cursor.getString(index14);
			int index15 = cursor.getColumnIndex(DatabaseHelper.COLUMN_RATING);
			String str15 = cursor.getString(index15);

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
			pulled_data.add(str14);
			pulled_data.add(str15);

		}

		return pulled_data;
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

	private String formatTheBadURL(String db_icon_url) {
		String return_url;
		return_url = db_icon_url.replaceAll("\\\\", "");
		Log.d("Formatted String", return_url);
		return return_url;
	}

	/////////////////////////////////////////////////////////////////////////////////
	//This class creates an object which extends to the database for commands
	//It is nested to encapsulate it
	static class DatabaseHelper extends SQLiteOpenHelper {

		//Standard variables for the database functions
		private static final String DATABASE_NAME = "videogamesdb";
		private static final String TABLE_NAME = "videogamestable";
		private static final int DATBASE_VERSION = 7;
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
		// Stored as a boolean, checkbox for whether or not they have played it
		public static final String COLUMN_PLAYED_CHECKBOX = "played_checkbox";
		// Stored as a String, rating system using the android 'star' picker
		public static final String COLUMN_RATING = "rating";

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
				+ COLUMN_PLATFORM_ABBREVIATION + " VARCHAR(255),"
				+ COLUMN_PLAYED_CHECKBOX + " VARCHAR(255),"
				+ COLUMN_RATING + " VARCHAR(255)"
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
