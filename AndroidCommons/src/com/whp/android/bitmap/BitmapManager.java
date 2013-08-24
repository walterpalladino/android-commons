package com.whp.android.bitmap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class BitmapManager {

	public static void show (final String url, final ImageView imageView,
			final int width, final int height, boolean lazy) {
		
		if (lazy) {
//			Bitmap spinner = BitmapFactory.decodeResource(BasicApplication.getAppContext().getResources(), R.drawable.spinner);
//			BitmapLazyLoader.INSTANCE.setPlaceholder(spinner);*/
			BitmapLazyLoader.INSTANCE.loadBitmap( url, imageView, width, height);
		} else {
			BitmapManager.loadBitmap( url, imageView, width, height);
		}
	}
	
	public static void loadBitmap (final String url, final ImageView imageView,
			final int width, final int height) {
		
		URL url2 = null;
		try {
			url2 = new URL((url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			imageView.setImageBitmap(null);
			return ;
		}

		Bitmap mIcon1 = null;
		 try {
			 mIcon1 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			imageView.setImageBitmap(null);
			return ;
		}
		imageView.setImageBitmap(Bitmap.createScaledBitmap(mIcon1, width, height, false));
		 
	}
}
