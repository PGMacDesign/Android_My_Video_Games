package com.pgmacdesign.myvideogames;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/*
This class will allow users to rate their current games that they have and update said game in the
database.
 */
public class RateGamesActivity extends ActionBarActivity {

	//Fragment manager manages the respective fragments being opened, hidden, closed, etc
	FragmentManager manager;
	//ListView that holds game details
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("OnCreate", " Called");
		setContentView(R.layout.empty_activity);

		ActionBarActivity actionBar = new ActionBarActivity();

		actionBar.getSupportActionBar();

		//Fragment manager
		manager = getFragmentManager();

		ListFragment list_fragment = new ListFragment();
		//Adding the bundle in to determine which items will be listed at the end of the listview
		Bundle bundle = new Bundle();
		bundle.putString("last_field_option", "Rate");
		list_fragment.setArguments(bundle);
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.empty_view, list_fragment, "rate");
		transaction.addToBackStack("backstack");
		transaction.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_plus_button, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.home) {
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

