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
