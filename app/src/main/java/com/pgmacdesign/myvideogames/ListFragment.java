package com.pgmacdesign.myvideogames;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pgmacdesign.myvideogames.database.TheDatabase;

import java.util.ArrayList;
import java.util.List;

/**
This class will server as the fragment that works with the listview to have data
 */
public class ListFragment extends Fragment{

	//String defined below in the bundle passed in
	public String last_field_option;

	//The listview
	ListView listView;

	//Game Rating. Initialized at zero to prevent null pointers
	float game_rating = -1;

	//TEMP LISTS
	List<String> passed_list1 = new ArrayList<>();
	List<String> passed_list2 = new ArrayList<>();

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
		TheDatabase db = new TheDatabase(getActivity());
		passed_list1 = db.getSomeTableData();

		if (last_field_option.equalsIgnoreCase("List")){
			for (int i = 0; i < passed_list1.size(); i++){
				Log.d("PASSED LIST DATA: ", passed_list1.get(i).toString());
			}
		} else if (last_field_option.equalsIgnoreCase("Rate")){
			Toast.makeText(getActivity(), "To update a rating, click on the star and then click on the name of the game",Toast.LENGTH_LONG).show();
			for (int i = 0; i < passed_list1.size(); i++){
				Log.d("PASSED LIST DATA: ", passed_list1.get(i).toString());
			}
		}
		//TEMP LIST
		List<String> passed_list = new ArrayList<>();
		passed_list.add("1");
		passed_list.add("2");
		passed_list.add("3");
		passed_list.add("4");
		passed_list.add("5");

		Context context = getActivity();

		listView = (ListView) view.findViewById(R.id.data_list_view);

		listView.setAdapter(new CustomAdapter(context, passed_list));

		return view;
	}

	//In case we want to utilize a saved instance state (IE rotate screen)
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	//This custom adapter is used to fill the respective data into the listview
	class CustomAdapter extends BaseAdapter{

		List<String> passed_data = new ArrayList<>();
		Context context;
		int [] imageId;
		private LayoutInflater inflater = null;

		public CustomAdapter(Context context, List<String> passed_list) {
			this.passed_data = passed_list;
			this.context=context;
			inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			return passed_data.size();
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
			TextView tv_game_name, tv_console_name;
			ImageView imageView;

			//These will change depending on which fragment is opened
			LinearLayout changing_layout;
			RatingBar ratingBar;
			CheckBox checkBox;
		}

		//Custom view
		public View getView(final int position, View convertView, ViewGroup parent) {
			//A holder object to reference the textviews and imageviews
			Holder holder=new Holder();
			View rowView;
			rowView = inflater.inflate(R.layout.custom_list_view, null);

			holder.imageView = (ImageView) rowView.findViewById(R.id.custom_list_view_image);
			holder.tv_game_name = (TextView) rowView.findViewById(R.id.custom_list_view_game_name);
			holder.tv_console_name = (TextView) rowView.findViewById(R.id.custom_list_view_console);
			holder.changing_layout = (LinearLayout) rowView.findViewById(R.id.linear_changing);

			//These two are created, but only defined if the string matches
			holder.ratingBar = new RatingBar(context);
			//On a side note, why is the rating bar ridiculously huge? I mean, enormous and in the way huge...
			holder.checkBox = new CheckBox(context);
			//Check which fragment is being called
			if (last_field_option.equalsIgnoreCase("List")){
				holder.changing_layout.addView(holder.checkBox);
			} else if (last_field_option.equalsIgnoreCase("Rate")){
				holder.changing_layout.addView(holder.ratingBar);
			}

			//Loop through the list to see what is in it and set the different fields
			for (int i = 0; i < passed_data.size(); i++){
				holder.tv_game_name.setText("A TEST " + i);
				holder.tv_console_name.setText("B TEST " + i);
				holder.imageView.setImageResource(R.drawable.ic_launcher);

				//Check which fragment we are in
				if (last_field_option.equalsIgnoreCase("List")){
					holder.checkBox.setChecked(false);
				} else if (last_field_option.equalsIgnoreCase("Rate")){
					holder.ratingBar.setNumStars(0);
					holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
						//Handles the rating changes
						public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

							final int numStars = ratingBar.getNumStars();
							ratingBar.getRating() ;
							final float ratingBarStepSize = ratingBar.getStepSize();
							game_rating = ratingBarStepSize;
						}
					});
				}
			}

			rowView.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Log.d("ListView", " Has been clicked at: " + position);



					String gameID = "11111";

					//If this is the List fragment
					if (last_field_option.equalsIgnoreCase("List")){



					//If this is the Rate fragment
					} else if (last_field_option.equalsIgnoreCase("Rate")){
						//This means they chose a rating, enter it into the database
						if (game_rating != -1){
							//Update the data to the database
							TheDatabase db = new TheDatabase(getActivity());
							try {
								db.updateRating(gameID, game_rating);
							} catch (Exception e){
								e.printStackTrace();
							}
							//Close the resource
							try {
								db.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}


					}

				}
			});
			return rowView;
		}
	}
}
