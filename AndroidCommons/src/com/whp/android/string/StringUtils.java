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
package com.whp.android.string;

/**
 * @author Walter Hugo Palladino
 * @since 28/12/2013
 * 
 */
public class StringUtils {

	/**
	 * isEmpty
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if (string == null) {
			return true;
		}
		String strTest = string.replaceAll ("\\s", "");
		if (strTest.length () == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isEmpty
	 * 
	 * @param string
	 * @param minLength
	 * @return
	 */
	public static boolean isEmpty(String string, int minLength) {
		if (string == null) {
			return true;
		}
		String strTest = string.replaceAll ("\\s", "");
		if (strTest.length () < minLength) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * trim
	 * 
	 * @param string
	 * @return
	 */
	public static String trim(String string) {
		return string.replaceAll ("\\s", "");
	}

	/**
	 * repeat
	 * 
	 * @param c
	 * @param size
	 * @return
	 */
	public static String repeat(char c, int size) {
		StringBuffer stringBuffer = new StringBuffer ();
		for (int n = 0; n < size; n++) {
			stringBuffer.append (c);
		}
		return stringBuffer.toString ();
	}
}
