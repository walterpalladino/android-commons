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
package com.whp.android.menu;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.whp.android.BasicApplication;

import android.content.res.XmlResourceParser;

/**
 * @author Walter Hugo Palladino
 * @since 12/07/2013
 *
 */
public class CustomMenuParser {

	public static List<CustomMenuItem> parseXml(int menu) {

		List<CustomMenuItem> menuItemList;
		menuItemList = new ArrayList<CustomMenuItem>();

	
		if (menu == 0)
			return null ;

		try {
			XmlResourceParser xpp = BasicApplication.getAppContext().getResources()
					.getXml(menu);

			xpp.next();
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					String elemName = xpp.getName();

					if (elemName.equals("item")) {

						String textId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"title");
						String iconId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"icon");
						String resId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"id");
						String actionClass = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"actionViewClass");

						CustomMenuItem item = new CustomMenuItem();
						
						item.setId( Integer.valueOf(resId.replace("@", "")) );
						if (iconId != null) {
							item.setImageId( Integer.valueOf(iconId.replace("@", "")) );
						}
						item.setCaption( resourceIdToString(textId) );
						item.setActionClass(actionClass);
						
						menuItemList.add(item);
					}

				}

				eventType = xpp.next();

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}

		return menuItemList ;
	}

	private static String resourceIdToString(String text) {

		if (!text.contains("@")) {
			return text;
		} else {
			String id = text.replace("@", "");
			return BasicApplication.getAppContext().getResources().getString(Integer.valueOf(id));
		}

	}
}
