package com.pgmacdesign.myvideogames;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/*
This class will list the user's games. They can scroll through them, add news ones, or click on
existing ones and view more details about them
 */
public class ListGamesActivity extends ActionBarActivity {

	//Fragment manager manages the respective fragments being opened, hidden, closed, etc
	FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.empty_activity);

		manager = getFragmentManager();


		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container_main, new PlaceholderFragment())
					.commit();
		}
		*/
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
			AddNewGameFragment addNewGameFragment = new AddNewGameFragment();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(R.id.empty_view, addNewGameFragment, "add");
			transaction.commit();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_main, container, false);
			return rootView;
		}
	}
}
