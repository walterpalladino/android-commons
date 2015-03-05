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
package com.whp.android.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.whp.android.BasicApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class CheckServices {

	/*
	 *	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	 */
	@SuppressWarnings("static-access")
	public static boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) BasicApplication.getAppContext().getSystemService(BasicApplication.getAppContext().CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	/**
	 * 
	 * isNetworkConnected
	 *
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(BasicApplication.getAppContext().CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	public static String getPhoneNumber() {
		TelephonyManager telephonyManager =(TelephonyManager)BasicApplication.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}
	
	public static boolean checkSiteIsUp(String testUrl, String parameters) {
		
		if (parameters == null) {
			parameters	= "";
		}
		
		try{
		    URL url = new URL(testUrl + parameters);
		    executeReq(url);
		    }catch(Exception e){
		    	return false;
		    }
		
		return true;
	}
	
	private static void executeReq(URL urlObject) throws IOException{
	    HttpURLConnection conn = null;

	    conn = (HttpURLConnection) urlObject.openConnection();
	    conn.setReadTimeout(10000);//milliseconds
	    conn.setConnectTimeout(15000);//milliseconds
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);

	    // Start connect
	    conn.connect();
	}
	
}
