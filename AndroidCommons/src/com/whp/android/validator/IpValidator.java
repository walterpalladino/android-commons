/*
 * Copyright 2014 Walter Hugo Palladino
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
package com.whp.android.validator;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.whp.android.string.StringUtils;

/**
 * @author Walter Hugo Palladino
 * @since 11/9/2014
 *
 */
public class IpValidator {

	private static final String IP_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * validate
	 * 
	 * @param ip
	 * @return true valid ip, false invalid ip
	 */
	public static boolean validate (final String ip) {

		Pattern pattern = Pattern.compile(IP_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();

	}

	/**
	 * validatePort
	 *
	 * @param port
	 * @return
	 */
	public static boolean validatePort (final String port) {
		if (StringUtils.isEmpty(port)) {
			return false;
		}
		try {
			NumberFormat.getInstance().parse(port);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}
