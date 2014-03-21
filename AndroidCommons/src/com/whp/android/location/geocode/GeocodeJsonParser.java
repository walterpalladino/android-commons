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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Walter Hugo Palladino
 * @since 21/03/2014
 * 
 */
public class GeocodeJsonParser {

	private static final String STATUS_OK = "OK";

	public static GeocodeResult parse(JSONObject jObject) {

		// The final result
		GeocodeResult geocodeResult = new GeocodeResult ();

		// The returned places
		List <GeocodeAddress> addresses = new ArrayList <GeocodeAddress> ();
		GeocodeAddress address = null;

		// JSON Array representing Places
		JSONArray jAddresses = null;

		try {

			// Get the status
			String status = jObject.getString ("status");
			if (!STATUS_OK.equals (status)) {
				geocodeResult.setStatus (status);
				return geocodeResult;
			}

			// Addresses
			jAddresses = jObject.getJSONArray ("results");

			for (int i = 0; i < jAddresses.length (); i++) {

				address = getAddress ((JSONObject) jAddresses.get (i));

				addresses.add (address);
			}

			geocodeResult.setAddresses (addresses);
			geocodeResult.setStatus (GeocodeResult.RESULT_OK);

		} catch (JSONException e) {
			geocodeResult.setStatus (GeocodeResult.RESULT_JSON_PARSER_ERROR);
		} catch (Exception e) {
			geocodeResult.setStatus (GeocodeResult.RESULT_EXCEPTION);
		}

		return geocodeResult;
	}

	/**
	 * getAddress
	 * 
	 * @param jAddress
	 * @return
	 * @throws JSONException
	 */
	private static GeocodeAddress getAddress(JSONObject jAddress) throws JSONException {

		GeocodeAddress address = new GeocodeAddress ();

		address.setFormattedAddress (jAddress.getString ("formatted_address"));

		address.setTypes (getTypes (jAddress));

		return address;
	}

	/**
	 * getTypes
	 * 
	 * @param jAddress
	 * @return
	 */
	private static List <String> getTypes(JSONObject jAddress) throws JSONException {
		List <String> types = new ArrayList <String> ();

		JSONArray jTypes = jAddress.getJSONArray ("types");

		for (int i = 0; i < jTypes.length (); i++) {
			types.add (jTypes.get (i).toString ());
		}
		return types;
	}

}
