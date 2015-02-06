package com.pgmacdesign.myvideogames.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgmacdesign.myvideogames.gson.jsonobjects.VideoGames;

/**
This class utilizes GSON to deserialize a JSON request using the VideoGames class
 */
public class DeserializeRequest {

	//Returns a VideoGames object after deserializing the data. @Params, The JSON String to be deserialized
	public static VideoGames deserializeTheJSON(VideoGames videoGames, String JSONString){

		//GSON object to assist with deserialization
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		try {
			//Deserialize the data and put the data into the videogames object
			//(This has got to be the most useful thing ever btw)
			videoGames = gson.fromJson(JSONString, VideoGames.class);
		} catch (Exception e){
			e.printStackTrace();
		}

		//Return the object
		return videoGames;
	}
}
