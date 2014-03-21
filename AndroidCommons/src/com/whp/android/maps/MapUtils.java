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
package com.whp.android.maps;

import com.whp.android.location.LocationData;

/**
 * @author Walter Hugo Palladino
 * @since 28/02/2014
 * 
 */
public class MapUtils {

	// http://maps.googleapis.com/maps/api/staticmap?center=-34.598538,-58.491786&zoom=13&size=400x200&markers=color:green%7Clabel:Gigly+kits%7C-34.598538,-58.491786&sensor=false
	/**
	 * Property Name : STATIC_MAPS_URL
	 * 
	 */
	private static final String STATIC_MAPS_URL = "http://maps.googleapis.com/maps/api/staticmap?";

	/**
	 * getStaticMapUrl
	 * 
	 * @param location
	 * @param zoom
	 * @param width
	 * @param height
	 * @return
	 */
	public static String getStaticMapUrl(LocationData location, int zoom, int width, int height) {

		StringBuffer url = new StringBuffer ();

		url.append (STATIC_MAPS_URL);
		url.append ("center=").append (location.latitude).append (",").append (location.longitude);
		url.append ("&zoom=").append (zoom);
		url.append ("&size=").append (width).append ("x").append (height);
		url.append ("&markers=size:normal%7color:red%7C").append (location.latitude).append (",")
				.append (location.longitude);
		url.append ("&sensor=false");

		return url.toString ();
	}

	/**
	 * getStaticMapUrl
	 * 
	 * @param locationOrigin
	 * @param locationDestination
	 * @param zoom
	 * @param width
	 * @param height
	 * @return
	 */
	public static String getStaticMapUrl(LocationData locationOrigin, LocationData locationDestination, int zoom,
			int width, int height) {

		StringBuffer url = new StringBuffer ();

		url.append (STATIC_MAPS_URL);
		url.append ("center=").append (locationDestination.latitude).append (",")
				.append (locationDestination.longitude);
		url.append ("&zoom=").append (zoom);
		url.append ("&size=").append (width).append ("x").append (height);
		url.append ("&markers=color:green%7Clabel:D%7C").append (locationDestination.latitude).append (",")
				.append (locationDestination.longitude);
		url.append ("&markers=color:red%7Clabel:O%7C").append (locationOrigin.latitude).append (",")
				.append (locationOrigin.longitude);
		url.append ("&sensor=false");

		return url.toString ();
	}

}
