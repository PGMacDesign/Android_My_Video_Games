package com.pgmacdesign.myvideogames.gson.jsonobjects;

import com.google.gson.annotations.SerializedName;

/**
This is the base element for all JSON nodes. It will house some strings and the object reference to
the results class, which houses a ton of extra data.
 */
public class VideoGames {

	public String error; //Hopefully will read "OK" if everything went right. Text string representation of the status_code
	public int limit; //Limit of results, Standards to 100
	public int number_of_page_results; //'nuff said
	public int number_of_total_results; //'nuff said
	public int status_code; //Integer indicating the result of the request. Below are values:
	/*
	Status Code values:
	1:OK
	100:Invalid API Key
	101:Object Not Found
	102:Error in URL Format
	103:'jsonp' format requires a 'json_callback' argument
	104:Filter Error
	105:Subscriber only video is for subscribers only
	 */

	public Results[] results; //It is returned as an array

	/**
	 Main JSON object node that holds the majority of the data. Strings match the JSON API return data
	 from the website (http://www.giantbomb.com/). This data will be parsed in using GSON to hold the data.
	 */
	static class Results {
		public String aliases; //Game may be called something else (IE Final Fantasy 7, may also be FF7 or FFVII)
		public String api_detail_url; //URL for the game in their api
		public String date_added; //Date when added to their API, NOT the release date. in format: 2008-04-01 16:07:06
		public String date_last_updated; //Last updated. In format: 2015-02-02 10:45:13
		public String deck; //Short description of the game (IE quick summary)
		public String description; //VERY LONG description of the entire game. Like, the entire wiki on it
		@SerializedName("id")
		public int game_id; //The game ID. and integer. IE, 13053
		public String name; //Name of the game, IE Final Fantasy 7
		public int number_of_user_reviews; //int, IE 25
		public String original_release_date; //Date game was released, In this format:  1997-01-31 00:00:00

		@SerializedName("image")
		public ImageClass game_image; //This class houses the strings for the links for the images of the games
		public Platforms[] platforms; //The different platforms the game is on(IE: PS1, PS2, SNES). It is returned as an array
	}

	//This class houses the strings for the links for the images of the games
	static class ImageClass {
		public String icon_url;
		public String medium_url;
		public String screen_url;
		public String small_url;  //These are all different URL links to different size images of the game
		public String super_url;
		public String thumb_url;
		public String tiny_url;
	}

	//The different platforms the game is on(IE: PS1, PS2, SNES). It is returned as an array
	static class Platforms {
		@SerializedName("name")
		public String system_name; //System name, IE, Playstation or PC, or Super Nintendo
		public String abbreviation; //Abbreviation of the system name, IE PC, PS1, PS3, SNES
	}
}

