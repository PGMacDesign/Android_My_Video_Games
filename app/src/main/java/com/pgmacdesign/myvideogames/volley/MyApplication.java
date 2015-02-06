package com.pgmacdesign.myvideogames.volley;

import android.app.Application;
import android.content.Context;

/**
This class provides context for the application (My application). It is a representation of the
application itself and is referenced in the manifest file. It is used in the volley singleton class
 */
public class MyApplication extends Application {
	private static MyApplication sInstance;

	public void onCreate(){
		super.onCreate();
		sInstance = this;
	}

	//Returns the instance
	public static MyApplication getsInstance(){
		return sInstance;
	}

	//Returns the context
	public static Context getAppContext(){
		return sInstance.getApplicationContext();
	}
}
