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
package com.whp.android.splashscreen;

import java.io.IOException;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.whp.android.BasicActivity;
import com.whp.android.ImageManager;
import com.whp.android.Log;
import com.whp.android.commons.R;

/**
 * @author Walter Hugo Palladino
 * @since 06/07/2013
 * 
 */
public class SplashScreenActivity extends BasicActivity {

	private static final String TAG = SplashScreenActivity.class.getCanonicalName ();

	protected Runnable postDelayedAction;

	protected long splashScreenDelay = 2000L;

	protected String splash_image_list = "splash_image_list";

	protected String nextActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		Log.d (TAG, "onCreate");

		super.onCreate (savedInstanceState);
		setContentView (R.layout.splash_screen);

		try {
			ApplicationInfo ai = getPackageManager ().getApplicationInfo (getPackageName (),
					PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			Log.d (TAG, "splashScreenDelay string : " + (String) bundle.get ("splash_screen_delay"));
			splashScreenDelay = Long.parseLong ((String) bundle.get ("splash_screen_delay"));
			Log.d (TAG, "splashScreenDelay : " + splashScreenDelay);

			nextActivity = bundle.getString ("splash_screen_next_activity");
			Log.d (TAG, "nextActivity : " + nextActivity);

			splash_image_list = bundle.getString ("splash_image_list");
			Log.d (TAG, "splash_image_list : " + splash_image_list);

		} catch (NameNotFoundException e) {
			Log.e (TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage ());
		} catch (NullPointerException e) {
			Log.e (TAG, "Failed to load meta-data, NullPointer: " + e.getMessage ());
		} catch (NumberFormatException e) {
			Log.e (TAG, "Failed to load meta-data, NumberFormatException: " + e.getMessage ());

		}

		doStuff ();

	}

	@SuppressWarnings("rawtypes")
	protected void switchActivity() throws ClassNotFoundException {

		Class classz = null;
		try {
			classz = Class.forName (nextActivity);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException ("Class " + nextActivity + " not found.");
		}

		// start the home screen
		Intent intent = new Intent (SplashScreenActivity.this,
		// LoginActivity.class);
				classz);
		SplashScreenActivity.this.startActivity (intent);
	}

	private ImageView imgSplashScreen;
	private String [] imageArray;
	private Handler handler;

	private void doStuff() {
		Log.d (TAG, "doStuff");

		imgSplashScreen = (ImageView) findViewById (R.id.imgSplashScreen);

		Resources r = getResources ();
		// imageArray = r.getStringArray (R.array.splash_image_list);
		int imageArrayId = getResources ().getIdentifier ("splash_image_list", "array", getPackageName ());
		imageArray = r.getStringArray (imageArrayId);

		if (imageArray.length > 0) {
			try {
				ImageManager.showImage (imgSplashScreen, imageArray[0]);
			} catch (IOException e) {
				Log.d (TAG, "run IOException" + e.getMessage ());
			}
		}

		handler = new Handler ();

		Runnable runnable = new Runnable () {

			int i = 1;

			@Override
			public void run() {

				if (imageArray.length > 1) {
					try {
						ImageManager.showImage (imgSplashScreen, imageArray[i]);
					} catch (IOException e) {
						Log.d (TAG, "run IOException" + e.getMessage ());
					}
				}
				i++;
				if (i > imageArray.length - 1) {
					// make sure we close the splash screen so the user won't
					// come
					// back when it presses back key
					finish ();
					try {
						switchActivity ();
					} catch (ClassNotFoundException e) {
						Log.d (TAG, "run IOException" + e.getMessage ());
					}
				} else {
					handler.postDelayed (this, splashScreenDelay);
				}

			}
		};

		// run a thread after 2 seconds to start the home screen
		handler.postDelayed (runnable, splashScreenDelay);
	}

}
