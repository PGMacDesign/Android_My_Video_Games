package com.pgmacdesign.myvideogames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.pgmacdesign.myvideogames.database.TheDatabase;

import java.util.ArrayList;
import java.util.List;

/**
This class shows the user more information about their games
 */
public class DetailsActivity extends Activity {

	//to hold the data parsed in via the database
	List<String> pulled_data = new ArrayList<>();
	//For setting the background image
	RelativeLayout relativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_activity);

		String game_id;

		Intent intent = getIntent();
		intent.getExtras();
		game_id = intent.getStringExtra("game_id");

		TheDatabase db = new TheDatabase(this);

		try {
			pulled_data = db.getRow(game_id);
		} catch (Exception e){
			e.printStackTrace();
		}


		//For setting the background image
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout_details);
		//Set the background
		//Picasso.with(this).load(pulled_data.get(4)).into(relativeLayout);


		/*

		To Do:

-Create image adapter to square up side images
-Design fragment to re-populate data from the new added game into list view
-add checkchangelistener for the check boxes and rating tool to send database call

~~Extra~~
-Create clickable portion of list view (Name and picture) that will open new page with video game data (whatever is available)

 -Debug



		 */
	}

}
