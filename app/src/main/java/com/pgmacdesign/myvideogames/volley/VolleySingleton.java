package com.pgmacdesign.myvideogames.volley;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
This singleton class is designed to allow for 1 instance of the Volley send requests. This is done
as per the documentation recommending a singleton instead of allowing multiple instances
 */
public class VolleySingleton {

	private static VolleySingleton sIntance = null;
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private VolleySingleton(){
		//Not passing an activity context (IE Context context), need the actual application context
		mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
		mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
			/*
			Size is defined in the last parameter entry. Instead of hard-coding in 4mb (or Xmb),
			We are defining it via referencing the runtime memory object, which maps memory. It then
			needs to be divided by 1024 to convert to bytes and then by 8 for bits. Lastly, it needs
			to be cast as an Int.
			 */
			private LruCache<String, Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

			@Override
			public Bitmap getBitmap(String url) {
				return cache.get(url);
			}

			//Puts the bitmap into the respective string URL keyValue pair defined in LruCache
			public void putBitmap(String url, Bitmap bitmap) {
				cache.put(url, bitmap);
			}
		});

		/*
		Use the LruCache. It holds strong references to a limited number of values. Each time a value
		is accessed, it is moved to the head of the queue. When the cache is full, the end of the queue
		is evicted and becomes eligible for garbage collection.
		 */

	}

	//Static method
	public static VolleySingleton getsInstance(){
		if (sIntance == null){
			sIntance = new VolleySingleton();
		}
		return sIntance;
	}

	//Returns the requestqueue
	public RequestQueue getmRequestQueue(){
		return mRequestQueue;
	}

	//Returns an ImageLoader object
	public ImageLoader getImageLoader(){
		return mImageLoader;
	}
}
