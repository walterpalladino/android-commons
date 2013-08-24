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
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;


public enum BitmapLazyLoader {
	
	INSTANCE;

	private final Map<String, SoftReference<Bitmap>> cache;
	private final ExecutorService pool;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	private Bitmap placeholder;

	BitmapLazyLoader() {
		cache = new HashMap<String, SoftReference<Bitmap>>();
		pool = Executors.newFixedThreadPool(5);
	}

	public void setPlaceholder(Bitmap bmp) {
		placeholder = bmp;
	}

	public Bitmap getBitmapFromCache(String url) {
		if (cache.containsKey(url)) {
			return cache.get(url).get();
		}
		return null;
	}

	private void queueJob(final String url, final ImageView imageView,
			final int width, final int height) {
		
		//	Create handler in UI thread.
		final Handler handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				
				String tag = imageViews.get(imageView);
				if (tag != null && tag.equals(url)) {
					if (msg.obj != null) {
						imageView.setImageBitmap((Bitmap) msg.obj);
					} else {
						imageView.setImageBitmap(placeholder);
						Log.d(null, "fail " + url);
					}
				}
			}
		};

		pool.submit(new Runnable() {
			@Override
			public void run() {
				final Bitmap bmp = downloadBitmap(url, width, height);
				Message message = Message.obtain();
				message.obj = bmp;
				Log.d(null, "Item downloaded: " + url);

				handler.sendMessage(message);
			}
		});
	}

/*	
	private void broadcastResult (Message msg) {
		
		Intent broadcast = new Intent();

		broadcast.putExtra("url", "result");
		
		broadcast.setAction("LAZY_LOAD_IMAGE_STATUS_OK");

		BasicApplication.getAppContext().sendBroadcast(broadcast);

	}
	*/
	
	
	public void loadBitmap(final String url, final ImageView imageView,
			final int width, final int height) {
		
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);

		// check in UI thread, so no concurrency issues
		if (bitmap != null) {
			Log.d(null, "Item loaded from cache: " + url);
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageBitmap(placeholder);
			queueJob(url, imageView, width, height);
		}
		
	}

	private Bitmap downloadBitmap(String url, int width, int height) {
		try {
			Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
					url).getContent());
			
			//	Try to adjust aspect in case of need
			int bitmapWidth		= bitmap.getWidth();
			int bitmapHeight	= bitmap.getHeight();
			
			float	aspectOriginal	= (float)bitmapWidth / (float)bitmapHeight;
			float	aspectRequired	= (float)width / (float)height;
			
			if (aspectOriginal < aspectRequired) {
				width	*= aspectOriginal;
			} else {
				height	/= aspectOriginal;
			}

			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			cache.put(url, new SoftReference<Bitmap>(bitmap));
			
			return bitmap;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		cache.remove(url);
		return null;
	}
}
