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
package com.whp.android.location;

import java.io.Serializable;
import java.util.Calendar;

import android.location.Location;

/**
 * @author Walter Hugo Palladino
 * @since 26/12/2013
 * 
 */
public class LocationData implements Serializable {

	/**
	 * Property Name : serialVersionUID
	 * 
	 */
	private static final long serialVersionUID = -1544988323402027137L;

	public double latitude;
	public double longitude;
	public Calendar acquiredDate;
	public String provider;
	public float accuracy;
	public double altitude;

	/**
	 * Constructor
	 */
	public LocationData () {
		Calendar date = Calendar.getInstance ();
		this.acquiredDate = date;
	}

	/**
	 * Constructor
	 * @param latitude
	 * @param longitude
	 */
	public LocationData (double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		Calendar date = Calendar.getInstance ();
		this.acquiredDate = date;
	}

	/**
	 * Constructor
	 * 
	 * @param location
	 */
	public LocationData (Location location) {

		this.latitude = location.getLatitude ();
		this.longitude = location.getLongitude ();
		Calendar date = Calendar.getInstance ();
		date.setTimeInMillis (location.getTime ());
		this.acquiredDate = date;
		this.provider = location.getProvider ();
		this.accuracy = location.getAccuracy ();
		this.altitude = location.getAltitude ();
	}

	@Override
	public String toString() {
		return "" + this.latitude + "/" + this.longitude + "/" + this.altitude + "/" + this.accuracy + "/"
				+ this.provider;
	}
}
