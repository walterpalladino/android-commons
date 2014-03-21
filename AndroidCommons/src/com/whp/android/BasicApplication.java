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

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.whp.android.security.SecurityUtils;

/**
 * @author Walter Hugo Palladino
 * @since 06/07/2013
 * 
 */
public class BasicApplication extends Application {

	private static final String TAG = BasicApplication.class.getCanonicalName ();

	public static String APPLICATION_STATUS_CHANGED = "com.whp.application.statuschanged";
	public static String APPLICATION_STATUS = "com.whp.application.status";
	public static String APPLICATION_STATUS_ACTIVE = "ACTIVE";
	public static String APPLICATION_STATUS_NOT_ACTIVE = "NOT_ACTIVE";

	protected static Context context;
	protected static int activitiesRunning = 0;

	@Override
	public void onCreate() {
		Log.d (TAG, "onCreate");
		super.onCreate ();
		BasicApplication.context = getApplicationContext ();

	}

	/**
	 * getAppContext
	 * @return
	 */
	public static Context getAppContext() {
		return BasicApplication.context;
	}

	/**
	 * getAlarmManager
	 * @return
	 */
	public static AlarmManager getAlarmManager() {
		return (AlarmManager) getAppContext ().getSystemService (Context.ALARM_SERVICE);
	}

	/**
	 * getActivitiesRunning
	 * @return
	 */
	public static int getActivitiesRunning() {
		return activitiesRunning;
	}

	/**
	 * setActivitiesRunning
	 * @param activitiesRunning
	 */
	private static void setActivitiesRunning(int activitiesRunning) {
		BasicApplication.activitiesRunning = activitiesRunning;
	}

	/**
	 * incActivitiesRunning
	 */
	public static void incActivitiesRunning() {

		if (getActivitiesRunning () == 0) {
			// This application is now active
			Log.d (TAG, "This application is now active");
			broadcastStatusChange (APPLICATION_STATUS_ACTIVE);
		}

		setActivitiesRunning (getActivitiesRunning () + 1);
		Log.d (TAG, "incActivitiesRunning : " + getActivitiesRunning ());
	}

	/**
	 * decActivitiesRunning
	 */
	public static void decActivitiesRunning() {
		if (getActivitiesRunning () > 0) {
			setActivitiesRunning (getActivitiesRunning () - 1);
		}
		Log.d (TAG, "decActivitiesRunning : " + getActivitiesRunning ());

		if (getActivitiesRunning () == 0) {
			// This application is no longer active
			Log.d (TAG, "This application is no longer active");
			broadcastStatusChange (APPLICATION_STATUS_NOT_ACTIVE);
		}
	}

	/**
	 * broadcastStatusChange
	 * @param data
	 */
	private static void broadcastStatusChange(String data) {
		Log.d (TAG, "broadcastStatusChange : " + data);

		Intent broadcast = new Intent ();

		Bundle bundle = new Bundle ();
		bundle.putString (APPLICATION_STATUS, data);

		broadcast.putExtras (bundle);
		broadcast.setAction (APPLICATION_STATUS_CHANGED);

		BasicApplication.context.sendBroadcast (broadcast);
	}
	
	/**
	 * getAppKeyHash
	 * @return
	 */
	public static String getAppKeyHash () {
		return SecurityUtils.getAppKeyHash (BasicApplication.getAppContext());
	}

}
