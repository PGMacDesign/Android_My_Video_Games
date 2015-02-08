package com.pgmacdesign.myvideogames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pgmacdesign.myvideogames.database.TheDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
This class shows the user more information about their games
 */
public class DetailsActivity extends ActionBarActivity {

	TheDatabase db;
	String game_id;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);

		ActionBarActivity actionBar = new ActionBarActivity();
		actionBar.getSupportActionBar();

		Intent intent = getIntent();
		intent.getExtras();
		game_id = intent.getStringExtra("game_id");

		db = new TheDatabase(this);

		//to hold the data parsed in via the database
		List<String> pulled_data = new ArrayList<>();

		try {
			//Fill the list with the row info
			pulled_data = db.getRow(game_id);
		} catch (Exception e){
			e.printStackTrace();
		}

		//All of the UI components
		TextView details_activity_game_name, details_activity_text_view_release_date,
				details_activity_description, details_activity_platform;
		ImageView imageView;
		RatingBar details_activity_ratingBar;
		CheckBox details_activity_checkbox;

		//Define the UI components
		details_activity_game_name = (TextView) findViewById(R.id.details_activity_game_name);
		details_activity_text_view_release_date = (TextView) findViewById(R.id.details_activity_text_view_release_date);
		details_activity_description = (TextView) findViewById(R.id.details_activity_description);
		details_activity_platform = (TextView) findViewById(R.id.details_activity_platform);

		imageView = (ImageView) findViewById(R.id.imageView);

		details_activity_checkbox = (CheckBox) findViewById(R.id.details_activity_checkbox);

		details_activity_ratingBar = (RatingBar) findViewById(R.id.details_activity_ratingBar);

		//Set the UI Components with values from the list/ database

		details_activity_game_name.setText(pulled_data.get(10));
		details_activity_text_view_release_date.setText("Release Date: " + pulled_data.get(11));
		details_activity_description.setText(pulled_data.get(2));
		details_activity_platform.setText("Platform: " + pulled_data.get(12) + " AKA: " + pulled_data.get(13));

		//Load the photo (higher quality link) into the image frame
		Picasso.with(this).load(pulled_data.get(4)).into(imageView);

		//Checkbox, see if database has it set to played or not
		String played = pulled_data.get(14);
		if (played.equalsIgnoreCase("true")){
			details_activity_checkbox.setChecked(true);
		} else {
			details_activity_checkbox.setChecked(false);
		}

		//Get the rating on the game
		String rating1 = pulled_data.get(15);
		//Just in case there are more significant figures, trim down to the first character (IE 1.0 vs 1)
		rating1 = rating1.substring(0, Math.min(rating1.length(), 1));
		//If the value is not a mistake, set the rating in the view window
		if (rating1.equalsIgnoreCase("-1.0")){
			Log.d("Rating negative on Game: ", game_id);
		} else {
			details_activity_ratingBar.setRating(Integer.parseInt(rating1));
		}

		db.close(); //Prevent memory leaks
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, ListGamesActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
