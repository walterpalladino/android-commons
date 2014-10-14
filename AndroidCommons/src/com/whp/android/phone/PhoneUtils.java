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
package com.whp.android.phone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author Walter Hugo Palladino
 * @since 4/10/2014
 *
 */
public class PhoneUtils {

	/**
	 * startPhoneCall
	 *
	 * @param context
	 * @param phoneNumber
	 * @return
	 */
	public static boolean startPhoneCall (Context context, String phoneNumber) {

		try {
			Uri callingUri = Uri.parse("tel:" + phoneNumber);
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(callingUri);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean startPhoneDial (Context context, String phoneNumber) {

		try {
			Uri telephoneUri = Uri.parse("tel:" + phoneNumber);
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(telephoneUri);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
