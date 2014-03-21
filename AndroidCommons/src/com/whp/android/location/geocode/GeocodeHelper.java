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
package com.whp.android.location.geocode;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.whp.android.location.LocationData;

/**
 * @author Walter Hugo Palladino
 * @since 21/03/2014
 * 
 */
public class GeocodeHelper {

	private static final String GEOCODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";

	/**
	 * getAddresses
	 * 
	 * @param location
	 * @param types
	 * @return
	 */
	public static GeocodeResult getAddresses(LocationData location, String types) {

		StringBuilder url = new StringBuilder ();

		url.append (GEOCODE_URL);
		url.append ("latlng=").append (location.latitude).append (",").append (location.longitude);
		url.append ("&sensor=false");

		GeocodeResult	geocodeResult	= getAddresses (url);
		if ((geocodeResult != null) && (GeocodeResult.RESULT_OK.equals (geocodeResult.getStatus ()))) {
			geocodeResult	= GeocodeHelper.filter (geocodeResult, types);
		}
		
		return geocodeResult;

	}

	/**
	 * getAddresses
	 * 
	 * @param url
	 * @return
	 */
	public static GeocodeResult getAddresses(StringBuilder url) {
		GeocodeResult result = null;

		try {

			HttpURLConnection httpURLConnection = null;
			StringBuilder stringBuilder = new StringBuilder ();

			httpURLConnection = (HttpURLConnection) new URL (url.toString ()).openConnection ();
			InputStreamReader inputStreamReader = new InputStreamReader (httpURLConnection.getInputStream ());

			int read;
			char [] buff = new char [1024];
			while ((read = inputStreamReader.read (buff)) != -1) {
				stringBuilder.append (buff, 0, read);
			}
			httpURLConnection.disconnect ();

			JSONObject jObject = new JSONObject (stringBuilder.toString ());

			result = GeocodeJsonParser.parse (jObject);

		} catch (Exception e) {
			e.printStackTrace ();
		}
		return result;
	}
	
	public static GeocodeResult filter (GeocodeResult result, String types) {
		GeocodeResult geocodeResult	= new GeocodeResult();
		List<GeocodeAddress> addresses	= new ArrayList<GeocodeAddress>();
		for (GeocodeAddress address : result.getAddresses ()) {
			if (GeocodeHelper.matchType(address, types)) {
				addresses.add (address);
				geocodeResult.setAddresses (addresses);
				geocodeResult.setStatus (result.getStatus ());
				return geocodeResult;
			}
		}
		return null;
	}

	/**
	 * matchType
	 * @param address
	 * @param types
	 * @return
	 */
	private static boolean matchType(GeocodeAddress address, String types) {
		for (String type : address.getTypes ()) {
			if (type.equals (types)) {
				return true;
			}
		}
		return false;
	}
}
