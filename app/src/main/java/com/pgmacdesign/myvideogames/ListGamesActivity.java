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
This class will list the user's games. They can scroll through them, add news ones, or click on
existing ones and view more details about them
 */
public class ListGamesActivity extends ActionBarActivity {

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

		//Manages the backstack changes, useful for if they accidentally click back
		getFragmentManager().addOnBackStackChangedListener(getListener());

		ListFragment list_fragment = new ListFragment();
		FragmentTransaction transaction = manager.beginTransaction();
		//Adding the bundle in to determine which items will be listed at the end of the listview
		Bundle bundle = new Bundle();
		bundle.putString("last_field_option", "List");
		list_fragment.setArguments(bundle);
		transaction.add(R.id.empty_view, list_fragment, "list");
		//transaction.addToBackStack("backstack");
		transaction.commit();

	}

	//This class is called whenever the backstack is changed. primarily used to handle the back button being hit
	private FragmentManager.OnBackStackChangedListener getListener(){
		FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
			public void onBackStackChanged() {
				ListFragment list_fragment = new ListFragment();
				//Adding the bundle in to determine which items will be listed at the end of the listview
				Bundle bundle = new Bundle();
				bundle.putString("last_field_option", "List");
				list_fragment.setArguments(bundle);
				FragmentTransaction transaction = manager.beginTransaction();
				transaction.replace(R.id.empty_view, list_fragment, "list");
				transaction.commit();
			}
		};
		return result;
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

		//noinspection SimplifiableIfStatement
		if (id == R.id.add_new_game) {
			/*
			This creates a new transaction, opens up a new fragment, covering up the one on screen
			and opens up one where the user can add a new game to the list
			 */
			AddNewGameFragment2 addNewGameFragment = new AddNewGameFragment2(); //FIX IT HERE WHEN UPDATE
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(R.id.empty_view, addNewGameFragment, "List");
			transaction.addToBackStack("backstack");
			transaction.commit();
			return true;
		}

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
