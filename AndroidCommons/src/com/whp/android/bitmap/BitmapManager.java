/*
 * Copyright 2013 Walter Hugo Palladino
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.whp.android.bitmap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * @author Walter Hugo Palladino
 * @since 24/02/2014
 *
 */
public class BitmapManager {

	/**
	 * show
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 * @param lazy
	 */
	public static void show (final String url, final ImageView imageView,
			final int width, final int height, boolean lazy) {
		
		if (lazy) {
//			Bitmap spinner = BitmapFactory.decodeResource(BasicApplication.getAppContext().getResources(), R.drawable.spinner);
//			BitmapLazyLoader.INSTANCE.setPlaceholder(spinner);*/
			BitmapLazyLoader.INSTANCE.loadBitmap( url, imageView, width, height, false);
		} else {
			BitmapManager.loadBitmap( url, imageView, width, height, false);
		}
	}
	
	/**
	 * show
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 * @param lazy
	 * @param crop
	 */
	public static void show (final String url, final ImageView imageView,
			final int width, final int height, boolean lazy, boolean crop) {
		
		if (lazy) {
//			Bitmap spinner = BitmapFactory.decodeResource(BasicApplication.getAppContext().getResources(), R.drawable.spinner);
//			BitmapLazyLoader.INSTANCE.setPlaceholder(spinner);*/
			BitmapLazyLoader.INSTANCE.loadBitmap( url, imageView, width, height, crop);
		} else {
			BitmapManager.loadBitmap( url, imageView, width, height, crop);
		}
	}
	
	/**
	 * loadBitmap
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 * @param crop
	 */
	public static void loadBitmap (final String url, final ImageView imageView,
			final int width, final int height, final boolean crop) {
		
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
