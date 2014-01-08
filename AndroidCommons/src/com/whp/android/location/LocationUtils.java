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

/**
 * @author Walter Hugo Palladino
 * @since 26/12/2013
 * 
 */
public class LocationUtils {

	/**
	 * isBetterLocation
	 * 
	 * @param location
	 * @param currentBestLocation
	 * @param timeElapsedBetweenSamples
	 * @return
	 */
	public static boolean isBetterLocation(LocationData newLocation, LocationData oldLocation,
			long maxTimeElapsedBetweenSamples, long maxAccuracyDelta) {
		
		if (oldLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.acquiredDate.getTimeInMillis () - oldLocation.acquiredDate.getTimeInMillis ();
		boolean isSignificantlyNewer = timeDelta > maxTimeElapsedBetweenSamples;
		boolean isSignificantlyOlder = timeDelta < -maxTimeElapsedBetweenSamples;
		boolean isNewer = timeDelta > 0;

		// If it's been more than the maximum accepted since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.accuracy - oldLocation.accuracy);
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > maxAccuracyDelta;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider (newLocation.provider, oldLocation.provider);

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
			return true;
		}
		return false;
	}

	/**
	 * isSameProvider
	 * 
	 * Checks whether two providers are the same
	 * 
	 * @param provider1
	 * @param provider2
	 * @return
	 */
	public static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals (provider2);
	}

	/**
	 * getDistanceBetweenLocations
	 * 
	 * @param loc1
	 * @param loc2
	 * @return
	 */
	public static double getDistanceBetweenLocations(LocationData loc1, LocationData loc2) {
		double R = 6371; // Radius of the earth in km
		double deltaLat = deg2rad (loc2.latitude - loc1.latitude); // deg2rad
																	// below
		double deltaLon = deg2rad (loc2.longitude - loc1.longitude);
		double a = Math.sin (deltaLat / 2) * Math.sin (deltaLat / 2) + Math.cos (deg2rad (loc1.latitude))
				* Math.cos (deg2rad (loc2.latitude)) * Math.sin (deltaLon / 2) * Math.sin (deltaLon / 2);
		double c = 2 * Math.atan2 (Math.sqrt (a), Math.sqrt (1 - a));
		double d = R * c; // Distance in km
		return d;
	}

	/**
	 * deg2rad
	 * 
	 * @param deg
	 * @return
	 */
	public static double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}

	/**
	 * bearing
	 * 
	 * @param loc1
	 * @param loc2
	 * @return
	 */
	public static double getBearing(LocationData loc1, LocationData loc2) {
		double longitude1 = loc1.longitude;
		double longitude2 = loc2.longitude;
		double latitude1 = Math.toRadians (loc1.latitude);
		double latitude2 = Math.toRadians (loc2.latitude);
		double longDiff = Math.toRadians (longitude2 - longitude1);
		double y = Math.sin (longDiff) * Math.cos (latitude2);
		double x = Math.cos (latitude1) * Math.sin (latitude2) - Math.sin (latitude1) * Math.cos (latitude2)
				* Math.cos (longDiff);

		return (Math.toDegrees (Math.atan2 (y, x)) + 360) % 360;
	}
}
