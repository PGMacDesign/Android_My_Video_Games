package com.pgmacdesign.myvideogames;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
			}
		});

		//Check the saved instance state (in case they rotate it while typing)
		if (savedInstanceState == null){
			//Nothing, new call
		} else {
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

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		String game_name = edit_text_game_name.getText().toString();
		String console_name = edit_text_console_name.getText().toString();

		if (game_name != null){
			outState.putString("edit_text_game_name", game_name);
		}
		if (console_name != null){
			outState.putString("edit_text_console_name", console_name);
		}
	}
}
