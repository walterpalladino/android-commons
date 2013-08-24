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
