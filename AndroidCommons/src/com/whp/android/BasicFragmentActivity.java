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
package com.whp.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @author Walter Hugo Palladino
 * @since 06/07/2013
 * 
 */
public class BasicFragmentActivity extends FragmentActivity {

	private final String TAG = BasicFragmentActivity.class.getCanonicalName ();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
	}

	@Override
	protected void onRestart() {
		Log.d (TAG, "BasicFragmentActivity onRestart");
		super.onRestart ();
	}

	@Override
	protected void onResume() {
		Log.d (TAG, "BasicFragmentActivity onResume");
		BasicApplication.incActivitiesRunning ();
		super.onResume ();
	}

	@Override
	protected void onPause() {
		Log.d (TAG, "BasicFragmentActivity onPause");
		BasicApplication.decActivitiesRunning ();
		super.onPause ();
	}

	@Override
	protected void onDestroy() {
		Log.d (TAG, "BasicFragmentActivity onDestroy");
		// BaseApplication.decActivitiesRunning();
		super.onDestroy ();
	}
}
