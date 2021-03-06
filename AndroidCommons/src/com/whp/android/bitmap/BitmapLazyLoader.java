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
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.whp.android.Log;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public enum BitmapLazyLoader {

	INSTANCE;

	private final Map <String, SoftReference <Bitmap>> cache;
	private final ExecutorService pool;
	private Map <ImageView, String> imageViews = Collections.synchronizedMap (new WeakHashMap <ImageView, String> ());
	private Bitmap placeholder;

	BitmapLazyLoader () {
		cache = new HashMap <String, SoftReference <Bitmap>> ();
		pool = Executors.newFixedThreadPool (5);
	}

	public void setPlaceholder(Bitmap bmp) {
		placeholder = bmp;
	}

	public Bitmap getBitmapFromCache(String url) {
		if (cache.containsKey (url)) {
			return cache.get (url).get ();
		}
		return null;
	}

	private void queueJob(final String url, final ImageView imageView, final int width, final int height,
			final boolean crop) {

		// Create handler in UI thread.
		final Handler handler = new Handler () {

			@Override
			public void handleMessage(Message msg) {

				String tag = imageViews.get (imageView);
				if (tag != null && tag.equals (url)) {
					if (msg.obj != null) {
						imageView.setImageBitmap ((Bitmap) msg.obj);
					} else {
						imageView.setImageBitmap (placeholder);
						Log.d (null, "fail " + url);
					}
				}
			}
		};

		pool.submit (new Runnable () {

			@Override
			public void run() {
				final Bitmap bmp = downloadBitmap (url, width, height, crop);
				Message message = Message.obtain ();
				message.obj = bmp;
				Log.d (null, "Item downloaded: " + url);

				handler.sendMessage (message);
			}
		});
	}

	/*
	 * private void broadcastResult (Message msg) {
	 * 
	 * Intent broadcast = new Intent();
	 * 
	 * broadcast.putExtra("url", "result");
	 * 
	 * broadcast.setAction("LAZY_LOAD_IMAGE_STATUS_OK");
	 * 
	 * BasicApplication.getAppContext().sendBroadcast(broadcast);
	 * 
	 * }
	 */

	public void loadBitmap(final String url, final ImageView imageView, final int width, final int height,
			final boolean crop) {

		String bitmapUrl = null;

		if (url.contains ("?")) {
			bitmapUrl = url + "&crop=" + crop;
		} else {
			bitmapUrl = url + "?crop=" + crop;
		}

		imageViews.put (imageView, bitmapUrl);
		Bitmap bitmap = getBitmapFromCache (bitmapUrl);

		// check in UI thread, so no concurrency issues
		if (bitmap != null) {
			Log.d (null, "Item loaded from cache: " + bitmapUrl);
			imageView.setImageBitmap (bitmap);
		} else {
			imageView.setImageBitmap (placeholder);
			queueJob (bitmapUrl, imageView, width, height, crop);
		}

	}

	private Bitmap downloadBitmap(String url, int width, int height, boolean crop) {
		try {
			URL urlUrl = new URL(url);
			InputStream content = (InputStream)urlUrl.getContent();
			
			Bitmap bitmap = BitmapFactory.decodeStream ((InputStream) new URL (url).getContent ());

			if ((width > 0) && (height > 0)) {
				
				// Try to adjust aspect in case of need
				int bitmapWidth = bitmap.getWidth ();
				int bitmapHeight = bitmap.getHeight ();
	
				float aspectOriginal = (float) bitmapWidth / (float) bitmapHeight;
				float aspectRequired = (float) width / (float) height;
	
				if (aspectOriginal < aspectRequired) {
					width *= aspectOriginal;
				} else {
					height /= aspectOriginal;
				}
	
				bitmap = Bitmap.createScaledBitmap (bitmap, width, height, true);
				if (crop) {
					bitmap = getRoundedShape (bitmap, width, height);
				}
			}

			cache.put (url, new SoftReference <Bitmap> (bitmap));

			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace ();
		} catch (IOException e) {
			e.printStackTrace ();
		}

		cache.remove (url);
		return null;
	}

	/**
	 * getRoundedShape
	 * 
	 * @param scaleBitmapImage
	 * @return
	 */
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage,int targetWidth, int targetHeight) {
		
		Bitmap targetBitmap = Bitmap.createBitmap (targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas (targetBitmap);
		Path path = new Path ();
		path.addCircle (((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2,
				(Math.min (((float) targetWidth), ((float) targetHeight)) / 2), Path.Direction.CCW);

		canvas.clipPath (path);
		Bitmap sourceBitmap = scaleBitmapImage;
		canvas.drawBitmap (sourceBitmap, new Rect (0, 0, sourceBitmap.getWidth (), sourceBitmap.getHeight ()),
				new Rect (0, 0, targetWidth, targetHeight), null);
		return targetBitmap;
	}

}
