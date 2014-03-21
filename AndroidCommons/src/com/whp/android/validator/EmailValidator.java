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
package com.whp.android.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Walter Hugo Palladino
 * @since 19/03/2014
 * 
 */
public class EmailValidator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * validate
	 * 
	 * @param hex
	 * @return true valid hex, false invalid hex
	 */
	public static boolean validate(final String email) {

		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile (EMAIL_PATTERN);
		matcher = pattern.matcher (email);
		return matcher.matches ();

	}
}
