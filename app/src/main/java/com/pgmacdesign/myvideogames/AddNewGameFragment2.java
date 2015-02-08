package com.pgmacdesign.myvideogames;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pgmacdesign.myvideogames.database.TheDatabase;
import com.pgmacdesign.myvideogames.gson.DeserializeRequest;
import com.pgmacdesign.myvideogames.gson.jsonobjects.VideoGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 This class allows users to add a new game to the database
 */
public class AddNewGameFragment2 extends Fragment{

	//Button to add a new game
	Button add_new_game;

	//Long returned when passing into the database. If it returns with -1, the insert did not go through
	long returned_int;

	//ListGames Activity --TESTING
	ListGamesActivity listGamesActivity = new ListGamesActivity();

	/*
	2 Different video games objects. The first is to hold data from when a videogame is searched for
	and the parsed data is the search results. The second is the object that will be returned once
	the user has chosen their game and a new request has been sent / received from the server using
	the game id.
	 */
	VideoGames videoGames_search_object, videoGames_game_object;

	//Allows list to be accessed in onItemClicked
	List<Integer> search_game_list_id = new ArrayList<>();

	//Edit Texts, where the user can enter the game names
	EditText edit_text_game_name, edit_text_console_name;

	String[] facts;

	String search_response0, set_response0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//This Array will be used to show when the user presses the add game button
		facts = getActivity().getResources().getStringArray(R.array.video_game_facts);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Inflate the add new game layout view
		View view = inflater.inflate(R.layout.add_new_game_layout, container, false);

		//Set the text views up
		this.edit_text_game_name = (EditText) view.findViewById(R.id.edit_text_game_name);
		this.edit_text_console_name = (EditText) view.findViewById(R.id.edit_text_console_name);

		//Set the button up and initiate its onclicklistener
		this.add_new_game = (Button) view.findViewById(R.id.add_game_button);
		/*
		When the button is clicked, it sends a search request to the server with the data in the
		respective boxes and then pulls the results, parses them, and displays them for the user
		to choose from. If it is a game they actually want to add, clicking that listview item will
		then add the game to their database
		 */
		this.add_new_game.setOnClickListener(new View.OnClickListener() {
			//This will take the input data and search the web for the respective game
			public void onClick(View v) {
				//Disables the button in case they click it multiple times
				add_new_game.setEnabled(false);

				//These 4 lines of code hide the keyboard when the user presses the button
				InputMethodManager inputManager = (InputMethodManager)
						getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

				Toast.makeText(getActivity(), "Loading your results now", Toast.LENGTH_LONG).show();

				String game_name = edit_text_game_name.getText().toString();
				String console_name = edit_text_console_name.getText().toString();

				if (game_name.equalsIgnoreCase("") || console_name.equalsIgnoreCase("")){
				}  else {
					if (game_name == null || console_name == null){
					} else {

					}
				}
				//Check to make sure first that they typed something in
				if (game_name != null || console_name != null){
					if (game_name.equalsIgnoreCase("") || console_name.equalsIgnoreCase("")) {
						add_new_game.setEnabled(false);
					} else {


						int length = game_name.length();
						//If the String ends with a whitespace (IE the auto complete helped out), delete the whitespace
						if (Character.isWhitespace(game_name.charAt(length - 1))) {
							game_name = game_name.substring(0, game_name.length() - 1);
						}
						//Replace all remaining whitespace (IE space in a name, Final Fantasy) with an underscore
						game_name = game_name.replaceAll(" ", "_").toLowerCase();


						int length2 = console_name.length();
						//If the String ends with a whitespace (IE the auto complete helped out), delete the whitespace
						if (Character.isWhitespace(console_name.charAt(length2 - 1))) {
							console_name = console_name.substring(0, console_name.length() - 1);
						}
						//Replace all remaining whitespace (IE space in a name, Final Fantasy) with an underscore
						console_name = console_name.replaceAll(" ", "_").toLowerCase();

						//New async task to run in the background. Pass in the 2 strings
						new GameSearch(getActivity(), game_name, console_name).execute();

						Handler handler0 = new Handler();
						//Adds a short delay in order to allow for the internet to catch up
						handler0.postDelayed(new Runnable() {
							public void run() {
								try {
									//Choose a random fact from the string array
									String randomStr = facts[new Random().nextInt(facts.length)];

									//Setup a Dialog popup to entertain the user for the seconds until the server response comes
									/*
									(Side note, people's attention spans have gotten bad as even I need something
									to entertain me for 10 seconds these days... I should probably read more.
									 */
									DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											switch (which) {
												//If they hit close, it will dismiss this dialog box
												case DialogInterface.BUTTON_NEGATIVE:
													try {
														dialog.dismiss();
													} catch (Exception e) {
														e.printStackTrace();
													}
													break;
											}
										}
									};
									AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
									builder.setTitle("This may take up to 10 seconds, so here is a random fact:");
									builder.setMessage(randomStr).setNegativeButton("Close", dialogClickListener).show();

								} catch (NullPointerException e){
									Toast.makeText(getActivity(), "Oops! Something went wrong! Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
									e.printStackTrace();
								}

								//
							}
						}, (1000*1));
					}
				} else {
					add_new_game.setEnabled(false);
				}
			}
		});

		//Check the saved instance state (in case they rotate it while typing)
		if (savedInstanceState == null){
			//Nothing, new call
		} else {
			//Set strings to the respective edit text fields saved in the bundle
			String game_name = savedInstanceState.getString("edit_text_game_name");
			String console_name = savedInstanceState.getString("edit_text_console_name");

			//In case something goes wonky with the pulling of saved instance state
			try {
				edit_text_game_name.setText(game_name);
				edit_text_console_name.setText(console_name);
			} catch (NullPointerException e){
				e.printStackTrace();
			}
		}

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	//To save data using the saved instance state bundle. Mostly used for if a user rotates a screen,
	//Which causes the screen to lose the saved dat. This way, they won't lose what they were typing.
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//Get the string in the edit text fields
		String game_name = edit_text_game_name.getText().toString();
		String console_name = edit_text_console_name.getText().toString();

		//If the text is not null, then set it to the saved instance state
		if (game_name != null){
			outState.putString("edit_text_game_name", game_name);
		}
		if (console_name != null){
			outState.putString("edit_text_console_name", console_name);
		}
	}

	/*
	Async task. This is run in order to query the web server, pull data, parse it, compare it, load it up
	for the user to view, and manage the progress bar
	 */
	private class GameSearch extends AsyncTask<String, Long, Void> {

		private String async_game_name;
		//private String search_response;
		private VideoGames search_videogames;
		private Context context;
		private String async_console_name;

		public GameSearch(Context context, String aGame, String aConsole){
			this.async_game_name = aGame;
			this.async_console_name = aConsole;
			this.context = context;
		}

		// can use UI thread here
		protected void onPreExecute() {
			//this.dialog.setMessage("Wait\nSome SLOW job is being done...");
			//this.dialog.show();
			//Or add toasts here, it will pop up

		}

		// automatically done on worker thread (separate from UI thread)
		protected Void doInBackground(final String... args) {
			//Create the web URL that will be used for the outbound call
			String web_url = "http://www.giantbomb.com/api/search/?api_key=9be0ead91d814eeb64cc5fb0da481ce726a22400&query=";
			web_url += async_game_name + "&field_list=name,id,platforms&format=json";

			try {
				ServerRequest sr = new ServerRequest();
				search_response0 = sr.makeServiceCall(web_url, 1);
			} catch (Exception e){
				e.printStackTrace();
			}

			return null;
		}

		// can use UI thread here
		protected void onPostExecute(final Void unused) {


			//Deserialize the string and add the data to the object
			search_videogames = DeserializeRequest.deserializeTheJSON(search_videogames, search_response0);
			Log.d("RESPONSE", search_response0);
			//Set it equal to the global object so we can access it in the onItemClickListener
			videoGames_search_object = search_videogames;

			add_new_game.setEnabled(true);
			userMakesChoice(search_videogames, async_console_name);

		}
	}//AsyncTask


	//This is the springboard for a few different actions.
	private void userMakesChoice(VideoGames search_videogames, String console_choice) {
		//Fill the List with data to add to the listview
		List<String> search_game_list = new ArrayList<>();

		//Fill the search game list with items from the video game object
		for(int i = 0; i < search_videogames.results.length; i++){
			String the_game_name = search_videogames.results[i].game_name; //Game name

			/*
			As this list returns more than just games, some will not have the platform array. If we
			try to loop through this as well, it will cause a null pointer error. Therefore, we need
			to check if the resource_type is game or not before moving forward. Then we can make a
			Nested for loop to search through the inner loop of the platforms
			 */
			String resource_type_name = search_videogames.results[i].resource_type;
			int platforms = -1;
			//This try catch is to run because the for loop keeps breaking due to badly designed JSON on the server side
			try {
				if (search_videogames.results[i].platforms.length != 0)  //Check here if no games are showing up
					platforms = search_videogames.results[i].platforms.length;
			} catch (NullPointerException e){
				e.printStackTrace();
			}
			//Check that the resource is a game AND check that the platform int is not incorrect
			if (resource_type_name.equalsIgnoreCase("game") && platforms != -1) {
				for (int x = 0; x < search_videogames.results[i].platforms.length; x++) {

					String the_console_name = search_videogames.results[i].platforms[x].system_name; //Platform it was on
					String the_console_abb = search_videogames.results[i].platforms[x].abbreviation; //Abbreviation

					//Check to make sure it matches their console choice
					if (console_choice.equalsIgnoreCase(the_console_abb) || console_choice.equalsIgnoreCase(the_console_name)){
						//Combine the strings together
						String toAddToList = the_game_name + ", on Platform : " + the_console_name + "/ " + the_console_abb;
						//Add the new string to the list
						search_game_list.add(toAddToList);

						//Get the id for the game and add that to a list as well
						int game_id = search_videogames.results[i].game_id;
						search_game_list_id.add(game_id);
					} else {

					}

				}
			}

		}

		//Just in case...
		if (search_game_list.size() == 0){
			search_game_list.add("Oops! No results turned up! Try entering in the console name differently (IE PS1 instead of Playstation 1)");
			search_game_list_id.add(-1);
		}

		//Create the arrayAdapter that uses the list created above as the data
		ArrayAdapter<String> arrayAdapter =
				new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, search_game_list);
		//Create the listview
		final ListView listView = (ListView) getActivity().findViewById(R.id.add_game_listview);

		//Set the datapter
		listView.setAdapter(arrayAdapter);
		listView.setClickable(true);
		/*
		This item click listener manages the list view. When the user chooses a video game from
		the list, it is added to their database of video games
		 */
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				//Set the list to unclickable whenever they press it so that they cannot hit it multiple times
				listView.setClickable(false);

				//Position chosen will match the position of the lists created above.
				int gameID = search_game_list_id.get(position);

				//For debugging purposes
				Log.d("ID Chosen", Integer.toString(gameID));

				//If ID is -1, then they clicked on the "oops" error, skip it
				if (gameID == -1){
					Toast.makeText(getActivity(), "That game may not be correct, try a different one perhaps?", Toast.LENGTH_LONG).show();
					listView.setClickable(true);
				} else {
					//Get the game details from the server, set it into the list, and add it to the database
					new SetGame(gameID, getActivity()).execute();
					Toast.makeText(getActivity(), "Your game is being added to the list, please wait a moment", Toast.LENGTH_LONG).show();
				}
			}
		});
	}


	/*
	Second Async task. This is run in order to query the web server, pull data, parse it, compare it, load it up
	for the addition to the database, and then run switch back to the ListGamesActivity if successful
	*/
	private class SetGame extends AsyncTask<String, Long, Void> {

		//Game ID (IE 13048)
		private int game_ID_to_add;
		//Video games object to receive the response code
		private VideoGames async2_video_games;
		private Context context;
		private String game_response;

		public SetGame(int game_ID_to_add, Context context){
			this.game_ID_to_add = game_ID_to_add;
			this.context = context;
		}

		// can use UI thread here
		protected void onPreExecute() {

		}

		// automatically done on worker thread (separate from UI thread)
		protected Void doInBackground(final String... args) {

			//13053
			//Create the web URL that will be used for the outbound call
			String web_url = "http://www.giantbomb.com/api/games/?api_key=9be0ead91d814eeb64cc5fb0da481ce726a22400&filter=id:";
			web_url += game_ID_to_add + "&format=json";

			try {
				ServerRequest sr = new ServerRequest();
				set_response0 = sr.makeServiceCall(web_url, 1);


				/*
				//Deserialize the string and add the data to the object
				async2_video_games = DeserializeRequest.deserializeTheJSON(async2_video_games, game_response);
				Log.d("RESPONSE", game_response);
				//Set it equal to the global object so we can access it in the onItemClickListener
				videoGames_game_object = async2_video_games;
				 */

			} catch (Exception e){
				e.printStackTrace();
			}

			/*
			RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue(); //This is utilizing the singleton

			try {
				//One big StringRequest object. Sets the search_response string to the returned data
				StringRequest request = new StringRequest(Request.Method.GET, web_url, new Response.Listener<String>() {
					public void onResponse(String response) {
						//Get the response from the server (It will be a string)
						game_response = response;
						//Deserialize the string and add the data to the object
						async2_video_games = DeserializeRequest.deserializeTheJSON(async2_video_games, game_response);
						Log.d("RESPONSE", response);
						//Set it equal to the global object so we can access it in the onItemClickListener
						videoGames_game_object = async2_video_games;

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						try {
							Log.d("ERROR", error.getMessage());
						} catch (NullPointerException e){
							Toast.makeText(context, "Oops! Something went wrong! Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
							e.printStackTrace(); //In case there is a server glitch from their api
						}
					}
				});

				//Add it to the request queue
				requestQueue.add(request);

			} catch (Exception e){
				Toast.makeText(context, "Oops! Something went wrong! Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			*/

			return null;
		}

		// can use UI thread here
		protected void onPostExecute(final Void unused) {

			Log.d("467", set_response0);
			new DeserializeALLTheThings(context, set_response0, async2_video_games).execute();


		}
	}//AsyncTask

	/*
	Third async task. This deserializes the data using GSON. It isn't really necessary, but, there is
	really no harm in letting the process run on a background thread vs the UI thread
	 */
	private class DeserializeALLTheThings extends AsyncTask<Void, Void, Void>{

		private VideoGames videoGames_deserialize;
		private String das_response;
		private Context context;

		public DeserializeALLTheThings(Context context, String str, VideoGames vg){
			das_response = str;
			videoGames_deserialize = vg;
			this.context = context;
		}

		protected Void doInBackground(Void... params) {
			videoGames_deserialize = DeserializeRequest.deserializeTheJSON(videoGames_deserialize, das_response);
			Log.d("491", "fired");
			videoGames_game_object = videoGames_deserialize;

			return null;
		}

		protected void onPostExecute(Void aVoid) {

			new PassToDatabase(videoGames_deserialize, context).execute();

			super.onPostExecute(aVoid);
		}
	}


	/*
	Fourth async task. This sets the data to the database and then when finished, pops back to the
	previous screen by passing to a new one
	*/
	private class PassToDatabase extends AsyncTask<String, Long, Void> {

		private VideoGames db_video_games;
		private Context context;

		public PassToDatabase(VideoGames vg, Context context){
			this.db_video_games = vg;
			this.context = context;
		}

		// automatically done on worker thread (separate from UI thread)
		protected Void doInBackground(final String... args) {


			//Put the data into the database
			long returned_long = putDataInDatabase(db_video_games);

			return null;
		}

		// can use UI thread here
		protected void onPostExecute(final Void unused) {

			//Pop back to the previous fragment
			getFragmentManager().popBackStack();

		}
	}//AsyncTask





	//This class will put the data into the database. Returns the row number of the row put into
	private long putDataInDatabase(VideoGames videogames_database_info){
		//if it returns -1 at the end, database put was not successful.
		long success_or_not = -1;

		//Create a list of all the data to input into the database from the VideoGames Object
		String db_game_id = Integer.toString(videogames_database_info.results[0].game_id);
		String db_aliases = videogames_database_info.results[0].aliases;
		String db_deck = videogames_database_info.results[0].deck;
		String db_icon_url = videogames_database_info.results[0].game_image.icon_url;
		db_icon_url = formatTheBadURL(db_icon_url);
		String db_medium_url = videogames_database_info.results[0].game_image.medium_url;
		db_medium_url = formatTheBadURL(db_medium_url);
		String db_screen_url = videogames_database_info.results[0].game_image.screen_url;
		db_screen_url = formatTheBadURL(db_screen_url);
		String db_small_url = videogames_database_info.results[0].game_image.small_url;
		db_small_url = formatTheBadURL(db_small_url);
		String db_super_url = videogames_database_info.results[0].game_image.super_url;
		db_super_url = formatTheBadURL(db_super_url);
		String db_thumb_url = videogames_database_info.results[0].game_image.thumb_url;
		db_thumb_url = formatTheBadURL(db_thumb_url);
		String db_tiny_url = videogames_database_info.results[0].game_image.tiny_url;
		db_tiny_url = formatTheBadURL(db_tiny_url);
		String db_name = videogames_database_info.results[0].game_name;
		//Shortening this one as it adds seconds afterwards (semi-useless here)
		String str = videogames_database_info.results[0].original_release_date;
		String db_original_release_date = str.substring(0, Math.min(str.length(), 10)); //First 10 Characters
		String db_platform_name = videogames_database_info.results[0].platforms[0].system_name;
		String db_platform_abbreviation = videogames_database_info.results[0].platforms[0].abbreviation;

		//Add the data to a list
		List<String> input_data = new ArrayList<>();

		input_data.add(db_game_id);
		input_data.add(db_aliases);
		input_data.add(db_deck);
		input_data.add(db_icon_url);
		input_data.add(db_medium_url);
		input_data.add(db_screen_url);
		input_data.add(db_small_url);
		input_data.add(db_super_url);
		input_data.add(db_thumb_url);
		input_data.add(db_tiny_url);
		input_data.add(db_name);
		input_data.add(db_original_release_date);
		input_data.add(db_platform_name);
		input_data.add(db_platform_abbreviation);
		input_data.add("false"); //Have they played it yet, new game, so no
		input_data.add("0"); //Current rating, not rated yet (It can be changed in the ratings activity)

		//Create a database object
		TheDatabase db = new TheDatabase(getActivity());

		try {
			//Pass in the list to update the database
			success_or_not = db.insertData(input_data);
			Log.d("Database ", "updated successfully");
		} catch (Exception e){
			e.printStackTrace();
			Log.d("Database ", "update failed");
		}

		try {
			//Finally, close the db to prevent memory leaks
			db.close();
		} catch (Exception e){
			e.printStackTrace();
		}

		return success_or_not;
	}

	private String formatTheBadURL(String db_icon_url) {
		String return_url;
		return_url = db_icon_url.replaceAll("\\\\", "");
		Log.d("Formatted String", return_url);
		return return_url;
	}

}
