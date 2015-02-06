package com.pgmacdesign.myvideogames;

import android.app.Activity;
import android.os.Bundle;

/*
The Main activity, will allow users to choose between 2 options, going to the ListGamesActivity or
to the RateGamesActivity. There will be NO action bar on purpose.
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}



}
