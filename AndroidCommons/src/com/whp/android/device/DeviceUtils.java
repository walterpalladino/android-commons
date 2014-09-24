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

import android.content.Context;
import android.content.res.Configuration;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * @author Walter Hugo Palladino
 * @since 18/9/2014
 *
 */
public class DeviceUtils {

	/**
	 * isLandscapeMode
	 *
	 * @return
	 */
	public static boolean isLandscapeMode (Context context) {

		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		int rotation = display.getRotation();

		if (Surface.ROTATION_0 == rotation || Surface.ROTATION_180 == rotation) {
			if (DeviceUtils.isTablet(context)) {
				// This is a Tablet and it is in Landscape orientation
				return true;
			} else {
				// This is a Phone and it is in Portrait orientation
				return false;
			}
		} else {
			if (DeviceUtils.isTablet(context)) {
				// This is a Tablet and it is in Portrait orientation
				return false;
			} else {
				// This is a Phone and it is in Landscape orientation
				return true;
			}
		}
	}

	/**
	 * isTablet
	 *
	 * @param context
	 * @return
	 */
	public static boolean isTablet (Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

}
