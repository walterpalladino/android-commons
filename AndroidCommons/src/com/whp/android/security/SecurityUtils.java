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

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.util.Base64;

import com.whp.android.Log;

/**
 * @author Walter Hugo Palladino
 * @since 12/03/2014
 * 
 */
public class SecurityUtils {

	/**
	 * getAppKeyHash
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppKeyHash(Context context) {
		String hash = null;
		try {
			PackageInfo info = context.getPackageManager ().getPackageInfo (context.getPackageName (),
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance ("SHA");
				md.update (signature.toByteArray ());
				hash = Base64.encodeToString (md.digest (), Base64.DEFAULT);
				Log.d ("Your Package Name", context.getPackageName ());
				Log.d ("Your Tag", hash);
			}
		} catch (NameNotFoundException e) {
			Log.e ("Name not found Exception", e.toString ());
		} catch (NoSuchAlgorithmException e) {
			Log.e ("No such algorith Exception", e.toString ());
		} catch (Exception e) {
			Log.e ("Exception", e.toString ());
		}
		return hash;
	}

	/**
	 * Encodes a String in AES-128 with a given key
	 * 
	 * @param context
	 * @param password
	 * @param text
	 * @return String Base64 and AES encoded String
	 */
	public static String encode(Context context, String password, String text) {
		if (password.length () == 0 || password == null) {
			return null;
		}

		if (text.length () == 0 || text == null) {
			return null;
		}

		try {
			SecretKeySpec skeySpec = getKey (password);
			byte [] clearText = text.getBytes ("UTF8");

			// IMPORTANT TO GET SAME RESULTS ON iOS and ANDROID
			final byte [] iv = new byte [16];
			Arrays.fill (iv, (byte) 0x00);
			IvParameterSpec ivParameterSpec = new IvParameterSpec (iv);

			// Cipher is not thread safe
			Cipher cipher = Cipher.getInstance ("AES/CBC/PKCS7Padding");
			cipher.init (Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
			
			String encrypedValue = Base64.encodeToString (cipher.doFinal (clearText), Base64.NO_WRAP);

			return encrypedValue;

		} catch (InvalidKeyException e) {
			e.printStackTrace ();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace ();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace ();
		} catch (BadPaddingException e) {
			e.printStackTrace ();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace ();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace ();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace ();
		}
		return null;
	}

	/**
	 * Decodes a String using AES-128 and Base64
	 * 
	 * @param context
	 * @param password
	 * @param text
	 * @return decoded String
	 */
	public static String decode(Context context, String password, String text) {

		if (password.length () == 0 || password == null) {
			return null;
		}

		if (text.length () == 0 || text == null) {
			return null;
		}

		try {
			SecretKey key = getKey (password);

			// IMPORTANT TO GET SAME RESULTS ON iOS and ANDROID
			final byte [] iv = new byte [16];
			Arrays.fill (iv, (byte) 0x00);
			IvParameterSpec ivParameterSpec = new IvParameterSpec (iv);

			byte [] encrypedPwdBytes = Base64.decode (text, Base64.NO_WRAP);
			// cipher is not thread safe
			Cipher cipher = Cipher.getInstance ("AES/CBC/PKCS7Padding");
			cipher.init (Cipher.DECRYPT_MODE, key, ivParameterSpec);
			byte [] decrypedValueBytes = (cipher.doFinal (encrypedPwdBytes));

			String decrypedValue = new String (decrypedValueBytes);

			return decrypedValue;

		} catch (InvalidKeyException e) {
			e.printStackTrace ();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace ();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace ();
		} catch (BadPaddingException e) {
			e.printStackTrace ();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace ();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace ();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace ();
		}
		return "";
	}

	/**
	 * Generates a SecretKeySpec for given password
	 * 
	 * @param password
	 * @return SecretKeySpec
	 * @throws UnsupportedEncodingException
	 */
	public static SecretKeySpec getKey(String password) throws UnsupportedEncodingException {

		int keyLength = 128;
		byte [] keyBytes = new byte [keyLength / 8];
		// explicitly fill with zeros
		Arrays.fill (keyBytes, (byte) 0x0);

		// if password is shorter then key length, it will be zero-padded
		// to key length
		byte [] passwordBytes = password.getBytes ("UTF-8");
		int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
		System.arraycopy (passwordBytes, 0, keyBytes, 0, length);
		SecretKeySpec key = new SecretKeySpec (keyBytes, "AES");
		return key;
	}
}
