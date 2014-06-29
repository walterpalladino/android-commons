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
package com.whp.android.security;

import android.annotation.SuppressLint;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Walter Hugo Palladino
 * @since 27/06/2014
 * 
 */
public class CustomCrypt {

	/**
	 * encrypt
	 * 
	 * @param text
	 * @param iv
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String text, String iv, String key) throws Exception {
		if (text == null || text.length () == 0)
			throw new Exception ("Empty string");

		byte [] encrypted = null;

		IvParameterSpec ivspec = new IvParameterSpec (iv.getBytes ());
		SecretKeySpec keyspec = new SecretKeySpec (key.getBytes (), "AES");
		Cipher cipher = Cipher.getInstance ("AES/CBC/NoPadding");

		cipher.init (Cipher.ENCRYPT_MODE, keyspec, ivspec);

		encrypted = cipher.doFinal (padString (text).getBytes ());

		return CustomCrypt.bytesToHex (encrypted);
	}

	/**
	 * decrypt
	 * 
	 * @param code
	 * @param iv
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("TrulyRandom")
	public static String decrypt(String code, String iv, String key) throws Exception {
		if (code == null || code.length () == 0)
			throw new Exception ("Empty string");

		byte [] decrypted = null;

		IvParameterSpec ivspec = new IvParameterSpec (iv.getBytes ());
		SecretKeySpec keyspec = new SecretKeySpec (key.getBytes (), "AES");
		Cipher cipher = Cipher.getInstance ("AES/CBC/NoPadding");

		cipher.init (Cipher.DECRYPT_MODE, keyspec, ivspec);

		decrypted = cipher.doFinal (hexToBytes (code));

		return new String(decrypted);
	}

	/**
	 * bytesToHex
	 * 
	 * @param data
	 * @return
	 */
	private static String bytesToHex(byte [] data) {
		if (data == null) {
			return null;
		}

		int len = data.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16)
				str = str + "0" + java.lang.Integer.toHexString (data[i] & 0xFF);
			else
				str = str + java.lang.Integer.toHexString (data[i] & 0xFF);
		}
		return str;
	}

	/**
	 * hexToBytes
	 * 
	 * @param str
	 * @return
	 */
	private static byte [] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length () < 2) {
			return null;
		} else {
			int len = str.length () / 2;
			byte [] buffer = new byte [len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt (str.substring (i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * padString
	 * 
	 * @param source
	 * @return
	 */
	private static String padString(String source) {
		char paddingChar = ' ';
		int size = 16;
		int x = source.length () % size;
		int padLength = size - x;

		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}

		return source;
	}

}
