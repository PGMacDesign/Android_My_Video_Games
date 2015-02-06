package com.pgmacdesign.myvideogames;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.pgmacdesign.myvideogames.gson.DeserializeRequest;
import com.pgmacdesign.myvideogames.gson.jsonobjects.VideoGames;
import com.pgmacdesign.myvideogames.volley.VolleySingleton;

/**
This class allows users to add a new game to the database
 */
public class AddNewGameFragment extends Fragment{

	//Button to add a new game
	Button add_new_game;

	//Edit Texts, where the user can enter the game names
	EditText edit_text_game_name, edit_text_console_name;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Inflate the add new game layout view
		View view = inflater.inflate(R.layout.add_new_game_layout, container, false);

		//Set the text views up
		this.edit_text_game_name = (EditText) view.findViewById(R.id.edit_text_game_name);
		this.edit_text_console_name = (EditText) view.findViewById(R.id.edit_text_console_name);

		//Set the button up and initiate its onclicklistener
		this.add_new_game = (Button) view.findViewById(R.id.add_game_button);
		this.add_new_game.setOnClickListener(new View.OnClickListener() {
			//This will take the input data and search the web for the respective game
			public void onClick(View v) {

				String game_name = edit_text_game_name.getText().toString();
				String console_name = edit_text_console_name.getText().toString();

				//Check to make sure first that they typed something in
				if (game_name != null || console_name != null){
					//Also make sure it is not just empty space
					Log.d("name and console ", "are not null");
					if (game_name.equalsIgnoreCase("") || console_name.equalsIgnoreCase("")) {
					} else {

						Log.d("name and console ", "are not == nothing ");
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

						Log.d("Async ", "Should have hit");
					}
				}
			}
		});

		//Check the saved instance state (in case they rotate it while typing)
		if (savedInstanceState == null){
			//Nothing, new call
		} else {
			//Set strings to the respective edit text fields saved in the bundle
			String console_name = savedInstanceState.getString("edit_text_console_name");
			String game_name = savedInstanceState.getString("edit_text_game_name");

			//In case something goes wonky with the pulling of saved instance state
			try {
				edit_text_console_name.setText(console_name);
				edit_text_game_name.setText(game_name);
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

		private String async_console_name;
		private String async_game_name;
		private int game_id;
		private String search_response;
		private VideoGames search_videogames;
		private Context context;

		public GameSearch(Context context, String aGame, String aConsole){
			this.async_console_name = aConsole;
			this.async_game_name = aGame;
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
			Log.d("Do in background ", "fired");
			//Create the web URL that will be used for the outbound call
			String web_url = "http://www.giantbomb.com/api/search/?api_key=9be0ead91d814eeb64cc5fb0da481ce726a22400&query=";
			web_url += async_game_name + "&field_list=name,id,platforms&format=json";

			Log.d("Full URL is: ", web_url);

			RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue(); //This is utilizing the singleton

			try {
				//One big StringRequest object. Sets the search_response string to the returned data
				StringRequest request = new StringRequest(Request.Method.GET, web_url, new Response.Listener<String>() {
					public void onResponse(String response) {
						//Get the response from the server (It will be a string)
						search_response = response;
						//Deserialize the string and add the data to the object
						search_videogames = DeserializeRequest.deserializeTheJSON(search_videogames, search_response);
						Log.d("RESPONSE", response);

						//Pass the object into the activity that will inflate the list view and allow the user to choose
						userMakesChoice(search_videogames);

					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Log.d("ERROR", error.getMessage());
					}
				});

				//Add it to the request queue
				requestQueue.add(request);

			} catch (Exception e){
				Toast.makeText(context, "Oops! Something went wrong! Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			return null;
		}

		// periodic updates - it is OK to change UI
		@Override
		protected void onProgressUpdate(Long... value) {
			super.onProgressUpdate(value);
			//editText.append("\nworking..." + value[0] + " Seconds"); //Update an editText field if need be here
		}

		// can use UI thread here
		protected void onPostExecute(final Void unused) {
			//need to now update the listview with the new info. reference the video games object above

		}
	}//AsyncTask

	
	//This is the springboard for a few different actions.
	private void userMakesChoice(VideoGames search_videogames) {
		//First create a fragment that will utilize the listview in the empty space

	}

	/*
Second Async task. This is run in order to query the web server, pull data, parse it, compare it, load it up
for the user to view, and manage the progress bar
 */
	private class SetGame extends AsyncTask<String, Long, Void> {

		public SetGame(){

		}

		// can use UI thread here
		protected void onPreExecute() {

		}

		// automatically done on worker thread (separate from UI thread)
		protected Void doInBackground(final String... args) {

			return null;
		}

		// periodic updates - it is OK to change UI
		@Override
		protected void onProgressUpdate(Long... value) {
			super.onProgressUpdate(value);
			//editText.append("\nworking..." + value[0] + " Seconds"); //Update an editText field if need be here
		}

		// can use UI thread here
		protected void onPostExecute(final Void unused) {


		}
	}//AsyncTask

}
