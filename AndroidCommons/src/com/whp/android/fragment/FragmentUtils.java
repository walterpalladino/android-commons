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
package com.whp.android.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.whp.android.commons.R;




/**
 * @author Walter Hugo Palladino
 * @since 24/02/2014
 *
 */
public class FragmentUtils {

	/**
	 * showFragment
	 * 
	 * @param fragment
	 * @param tag
	 * @param addToBackStack
	 */
	public static void showFragment(FragmentActivity fragmentActivity, Fragment fragment, String tag, boolean addToBackStack) {

		FragmentTransaction ft = fragmentActivity.getSupportFragmentManager ().beginTransaction ();
		ft.setCustomAnimations (R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
		ft.replace (R.id.fragment_container, fragment, tag);
		if (addToBackStack) {
			ft.addToBackStack (tag);
		}
		ft.commit ();

	}
}
