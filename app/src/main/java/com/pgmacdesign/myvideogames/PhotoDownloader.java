package com.pgmacdesign.myvideogames;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

/**
Picasso did not want to play nicely so I am using this for now to download the images
 */
public class PhotoDownloader {

	public static Bitmap getBitmap(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Bitmap d = BitmapFactory.decodeStream(is);
			is.close();
			return d;
		} catch (Exception e) {
			return null;
		}
	}
}
