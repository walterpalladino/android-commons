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
package com.whp.android.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Walter Hugo Palladino
 * @since 14/06/2014
 * 
 */
public class KeyboardUtils {

	/**
	 * hideSoftKeyboard
	 * 
	 * @param activity
	 */
	public static void hideSoftKeyboard(Activity activity) {
		if (activity.getCurrentFocus () != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService (Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow (activity.getCurrentFocus ().getWindowToken (), 0);
		}
	}

	/**
	 * showSoftKeyboard
	 * 
	 * @param activity
	 * @param view
	 */
	public static void showSoftKeyboard(Activity activity, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService (Context.INPUT_METHOD_SERVICE);
		view.requestFocus ();
		inputMethodManager.showSoftInput (view, 0);
	}
}
