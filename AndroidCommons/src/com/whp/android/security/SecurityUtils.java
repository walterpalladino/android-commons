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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
}
