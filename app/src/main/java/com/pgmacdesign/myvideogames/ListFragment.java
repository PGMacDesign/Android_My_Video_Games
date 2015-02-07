package com.pgmacdesign.myvideogames;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pgmacdesign.myvideogames.database.TheDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
This class will server as the fragment that works with the listview to have data
 */
public class ListFragment extends Fragment{

	//CustomAdapter.Holder holder2; //May need to add this back in
	CustomAdapter.Holder holder;

	//String defined below in the bundle passed in
	public String last_field_option;

	//The listview
	ListView listView;

	//Game Rating. Initialized at zero to prevent null pointers
	int game_rating = -1;

	//onCreate
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//This retrieves the data passed by the bundle and sets the string last_field_option
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			last_field_option = bundle.getString("last_field_option", "List");
		}
	}

	//When the view is created
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//Inflate the add new game layout view
		View view = inflater.inflate(R.layout.list_view_holder, container, false);

		//Database object to pull data
		TheDatabase main_db = new TheDatabase(getActivity());

		//List of lists of strings. Essentially, holds all records
		List<List<String>> lists_of_records = new ArrayList<>();
		//List of the row numbers (in string format)
		List<String> passed_list_rows = new ArrayList<>();

		//Get the row numbers by querying the database
		passed_list_rows = main_db.getAllGameIDs();




		//Loop through the row numbers and add ALL of the items to the list of lists
		for (int i = 0; i < passed_list_rows.size(); i++){
			lists_of_records.add(main_db.getRow(passed_list_rows.get(i)));
		}


		/*
		for (int i = 0; i < lists_of_records.size(); i++){
			List<String> temp_list = lists_of_records.get(i);
			String photo_url = temp_list.get(3);

		}
		*/

		//Determine the passed string via the fragment bundle
		if (last_field_option.equalsIgnoreCase("List")){
			Log.d("Passed String is ", "List");

			//Determine the passed string via the fragment bundle
		} else if (last_field_option.equalsIgnoreCase("Rate")){
			Log.d("Passed String is ", "Rate");
			Toast.makeText(getActivity(), "To update a rating, click on the star and then click on the name of the game",Toast.LENGTH_LONG).show();

		}

		//Get the context to pass in
		Context context = getActivity();
		//Setup a listview and reference it to the listview id
		listView = (ListView) view.findViewById(R.id.data_list_view);
		//Set the custom adapter to the listview
		listView.setAdapter(new CustomAdapter(context, lists_of_records, main_db));

		//Close the database to prevent memory leaks
		try {
			main_db.close();
		} catch (Exception e){
			e.printStackTrace();
		}

		return view;
	}

	//In case we want to utilize a saved instance state (IE rotate screen)
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	//This custom adapter is used to fill the respective data into the listview
	class CustomAdapter extends BaseAdapter{

		List<List<String>> all_passed_data = new ArrayList<>();
		Context context;
		private LayoutInflater inflater = null;
		private TheDatabase db;

		public CustomAdapter(Context context, List<List<String>> passed_list, TheDatabase db1) {
			this.all_passed_data = passed_list;
			this.context=context;
			inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			db = db1;

		}

		@Override
		public int getCount() {
			return all_passed_data.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		//Holds the respective fields
		public class Holder {
			//These text views and image views will remain the same for both activities
			TextView tv_game_name, tv_console_name, tv_bottom;
			ImageView imageView;

			//These will change depending on which fragment is opened
			LinearLayout bottom_left, bottom_right;

			RatingBar ratingBar;
			CheckBox checkBox;
		}

		//Custom view
		public View getView(final int position, View convertView, ViewGroup parent) {


			//A holder object to reference the textviews and imageviews
			//Holder holder=new Holder();
			holder = new Holder(); //Was holder2, may need to change
			View rowView;
			rowView = inflater.inflate(R.layout.custom_list_view, null);

			Display display = getActivity().getWindowManager().getDefaultDisplay();
			DisplayMetrics outMetrics = new DisplayMetrics ();
			display.getMetrics(outMetrics);
			float density  = getResources().getDisplayMetrics().density;
			float dpHeight = outMetrics.heightPixels / density;
			float dpWidth  = outMetrics.widthPixels / density;

			holder.imageView = (ImageView) rowView.findViewById(R.id.custom_list_view_image);
			holder.imageView.setMaxWidth((int)(dpWidth)*(30/100));
			holder.imageView.setPadding(15, 15, 15, 15);

			holder.tv_game_name = (TextView) rowView.findViewById(R.id.custom_list_view_game_name);
			holder.tv_game_name.setMaxWidth((int)(dpWidth)*(30/100));

			holder.tv_console_name = (TextView) rowView.findViewById(R.id.custom_list_view_console);
			holder.tv_console_name.setMaxWidth((int) (dpWidth)*(35/100));

			holder.bottom_left = (LinearLayout) rowView.findViewById(R.id.left_layout);

			holder.bottom_right = (LinearLayout) rowView.findViewById(R.id.right_layout);

			holder.tv_bottom = new TextView(context);
			holder.ratingBar = new RatingBar(context); //On a side note, why is the rating bar ridiculously huge? I mean, enormous and in the way huge...
			holder.ratingBar.setStepSize(1);
			holder.ratingBar.setNumStars(4);
			holder.checkBox = new CheckBox(context);

			holder.tv_bottom.setPadding(5, 5, 5, 5); //Add some padding so they are not right up against each other

			//Check which fragment is being called
			if (last_field_option.equalsIgnoreCase("List")){ //Check in small (left), tv in large (right)
				holder.bottom_left.addView(holder.checkBox);
				holder.bottom_right.addView(holder.tv_bottom);

				holder.tv_bottom.setGravity(Gravity.CENTER); //Set to center so it aligns better
				holder.checkBox.setGravity(Gravity.CENTER); //Set to center so it aligns better
				holder.bottom_left.setGravity(Gravity.CENTER); //Set to center so it aligns better
				holder.checkBox.setPadding(5, 5, 5, 5);


				holder.tv_bottom.setText("Have you finished playing the game?");

			} else if (last_field_option.equalsIgnoreCase("Rate")){ //Rating in large (right), tv in small (left)
				holder.bottom_left.addView(holder.tv_bottom);
				holder.bottom_right.addView(holder.ratingBar);

				holder.tv_bottom.setGravity(Gravity.LEFT); //Set text to center so it aligns better
				holder.ratingBar.setPadding(5, 5, 5, 5);

				/*
				bottom_right_params.width = (int) ((dpWidth)*(15/100));
				holder.bottom_right.setLayoutParams(bottom_right_params);

				bottom_left_params.width = (int) ((dpWidth)*(85/100));
				holder.bottom_left.setLayoutParams(bottom_left_params);
				*/





				holder.tv_bottom.setText("Rate the game,\nthen click here");
			}

			//Loop through the list to see what is in it and set the different fields
			for (int i = 0; i < all_passed_data.size(); i++){
				List<String> temp_list = all_passed_data.get(position);

				String game_id = temp_list.get(0);

				final String passed_game_id = game_id;

				holder.tv_game_name.setText(temp_list.get(10)); //Game Name
				holder.tv_console_name.setText(temp_list.get(12)); //Platform Name

				//Setting the image using the third party library picasso
				String icon_url = temp_list.get(3);
				Log.d("Photo URL is: ", icon_url);

				//Set the image using a bitmap returned from the URL
				Picasso.with(context).load(icon_url).into(holder.imageView);

				//Check which fragment we are in
				if (last_field_option.equalsIgnoreCase("List")){
					holder.checkBox.setChecked(false);

					//Set the checkbox
					String played_game = temp_list.get(14);
					if (played_game.equalsIgnoreCase("true")){
						holder.checkBox.setChecked(true);
					} else {
						holder.checkBox.setChecked(false);
					}

					/*
					Add an on item click listener to the picture and the title of the game so
					if they click on it it will open a new activity with more details
					 */
					holder.imageView.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Log.d("Game ID is :", passed_game_id);
							Intent intent = new Intent(getActivity(), DetailsActivity.class);
							intent.putExtra("game_id", passed_game_id);
							startActivity(intent);
						}
					});

					//Same for title/ name as for image view, allows for a larger click area
					holder.tv_game_name.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Log.d("Game ID is :", passed_game_id);
							Intent intent = new Intent(getActivity(), DetailsActivity.class);
							intent.putExtra("game_id", passed_game_id);
							startActivity(intent);
						}
					});

					//Manages the checkbox and whether or not it is checked. Updates the database when clicked
					holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							String game_id = passed_game_id;

							if (isChecked){

								try {
									db.updatePlayedCheckbox(game_id, "true");
									Log.d("Database ", "Updated Successfully with boolean - true");

								} catch (Error e){
									e.printStackTrace();
									Log.d("Database ", "Was NOT updated with boolean");
								}

							} else {

								try {
									db.updatePlayedCheckbox(game_id, "false");
									Log.d("Database ", "Updated Successfully with boolean - false");
								} catch (Error e){
									e.printStackTrace();
									Log.d("Database ", "Was NOT updated with boolean");
								}

							}
						}
					});

					/*
					//Set the checkbox listener so if they add something it will change the database
					holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						//If they check it, update the database
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

							TheDatabase db = new TheDatabase(context);
							if (isChecked){
								db.updatePlayedCheckbox(passed_game_id, "true");
							} else {
								db.updatePlayedCheckbox(passed_game_id, "false");
							}

							//Close the database to prevent memory leaks
							try {
								db.close();
							} catch (Exception e){
								e.printStackTrace();
							}
						}
					});
					*/



				} else if (last_field_option.equalsIgnoreCase("Rate")){

					String rating = temp_list.get(15);
					holder.ratingBar.setRating(Integer.parseInt(rating));
					holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
						//Handles the rating changes
						public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

							float score = rating;
							if (score > 4){
								score = 4; //To prevent out of bounds issues
							}

							//Set the game rating to the score they chose
							game_rating = (int) score;
						}
					});
				}




				//MOVE THE ON CLICK LISTENER WITHIN THE IF STATEMENTS



				rowView.setOnClickListener(new View.OnClickListener() {

					public void onClick(View v) {
						Log.d("ListView", " Has been clicked at: " + position);


						//If this is the List fragment
						if (last_field_option.equalsIgnoreCase("List")){



							//If this is the Rate fragment
						} else if (last_field_option.equalsIgnoreCase("Rate")){
							//This means they chose a rating, enter it into the database
							if (game_rating != -1) {
							} else {
								try {
									db.updateRating(passed_game_id, game_rating);
								} catch (Exception e){
									e.printStackTrace();
								}
								//Close the resource
							}


						}

					}
				});
			}


			return rowView;
		}

	}

}
