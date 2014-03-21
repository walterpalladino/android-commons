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

import java.io.Serializable;
import java.util.List;

/**
 * @author Walter Hugo Palladino
 * @since 21/03/2014
 * 
 */
public class GeocodeResult implements Serializable {

	/**
	 * Property Name : serialVersionUID
	 * 
	 */
	private static final long serialVersionUID = -1106742423802217937L;

	public static final String RESULT_OK = "0";
	public static final String RESULT_JSON_PARSER_ERROR = "1";
	public static final String RESULT_EXCEPTION = "2";
	
	private List <GeocodeAddress> addresses;
	private String status;

	/**
	 * @return the addresses
	 */
	public List <GeocodeAddress> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 *            the addresses to set
	 */
	public void setAddresses(List <GeocodeAddress> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
