/*
 * Copyright 2014 Walter Hugo Palladino
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
package com.whp.android.device;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Surface;

/**
 * @author Walter Hugo Palladino
 * @since 18/9/2014
 *
 */
public class DeviceUtils {

	/**
	 * Checks if device is in landscape mode Some devices did not informs well his rotation so I decided to use a more raw way to
	 * check using device screen dimensions.
	 * 
	 * isLandscapeMode
	 *
	 * @return
	 */
	public static boolean isLandscapeMode (Context context) {

		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		float screenWidth = dm.widthPixels / dm.xdpi;
		float screenHeight = dm.heightPixels / dm.ydpi;

		return screenWidth > screenHeight;
	}

	/**
	 * Check if the device has a screen bigger that 6 inches
	 * 
	 * isTablet
	 *
	 * @param context
	 * @return
	 */
	public static boolean isTablet (Context context) {

		try {
			// Compute screen size
			DisplayMetrics dm = context.getResources().getDisplayMetrics();
			float screenWidth = dm.widthPixels / dm.xdpi;
			float screenHeight = dm.heightPixels / dm.ydpi;
			double size = Math.sqrt(Math.pow(screenWidth, 2) + Math.pow(screenHeight, 2));
			// Tablet devices should have a screen size greater than 6 inches
			return size >= 6;
		} catch (Throwable t) {
			return false;
		}

	}

	/**
	 * getStatusBarHeight
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight (Context context) {

		int titleBarHeight = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			titleBarHeight = context.getResources().getDimensionPixelSize(resourceId);
		}
		return titleBarHeight;
	}

	/**
	 * getUUID
	 *
	 * @param context
	 * @return UUID
	 */
	public static String getUUID (Context context) {
		TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tManager.getDeviceId();
	}

	/**
	 * 
	 * lockScreenOrientation
	 *
	 * @param activity
	 */
	public static void lockScreenOrientation (Activity activity) {

		int currentOrientation = activity.getResources().getConfiguration().orientation;
		int currentRotation = activity.getWindowManager().getDefaultDisplay().getRotation();

		if (currentRotation == Surface.ROTATION_0 || currentRotation == Surface.ROTATION_90) {
			if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			} else if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		} else if (currentRotation == Surface.ROTATION_180 || currentRotation == Surface.ROTATION_270) {
			if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
			} else if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
			}
		}
	}

	/**
	 * 
	 * unlockScreenOrientation
	 *
	 * @param activity
	 */
	public static void unlockScreenOrientation (Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}
}
