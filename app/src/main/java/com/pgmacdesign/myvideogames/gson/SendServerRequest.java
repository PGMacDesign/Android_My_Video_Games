package com.pgmacdesign.myvideogames.gson;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pgmacdesign.myvideogames.volley.VolleySingleton;

import org.json.JSONObject;

/**
This class sends server requests to the video games website to pull data and store it
 */
public class SendServerRequest {

	/*
	These will hold the reply from the server. I created 2 in case the device runs slow and the
	second request overlaps at the same time as the first. If this happens, it could criss cross
	the strings. Better to leave them isolated from one another.
	 */
	String server_reply1;
	String server_reply2;

	public String searchForGameResults(String game_search_string){
		server_reply1 = "";


		return server_reply1;
	}

	public String gameResults(int game_id){
		server_reply2 = "";


		return server_reply2;
	}

	private void searchForGameResultsRequest1(){
		//RequestQueue requestQueue = Volley.newRequestQueue(this); //You would use this if you did NOT have a Singleton class
		RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue(); //This is utilizing the singleton

		//This is a JSON POST Request with a json string (like the one made via Lacuna builders)
		try {
			//This string obtained from a different class. posting here for example
			String str = "{\"params\":[\"02b70b99-c2de-4539-bb4e-dfc3c3d74285\",{\"tags\":[\"Correspondence\"],\"page_number\":1}],\"jsonrpc\":\"2.0\",\"method\":\"view_inbox\",\"id\":1}";
			String url = "https://us1.lacunaexpanse.com/inbox"; //Change to empire to check for session id

			//Create a JSONObject (Not a gson JsonObject, different things)
			JSONObject jsonObject = new JSONObject(str); //Use str000 for fresh session id

			JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							// display response

							Log.d("Response: ", response.toString());
						}
					},
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.d("Error.Response: ", error.getMessage());
						}
					}
			);

			requestQueue.add(getRequest);
			Log.d("It DID", " Fire");

		} catch (Exception e){
			e.printStackTrace();
			Log.d("Did Not", " Fire");
		}
	}

	private void searchForGameResultsRequest2(){

	}


}
