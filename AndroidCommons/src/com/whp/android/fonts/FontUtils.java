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
package com.whp.android.fonts;

import java.util.EnumMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Walter Hugo Palladino
 * @since 13/09/2013
 * 
 */
public final class FontUtils {

	/**
	 * Property Name : DEFAULT_FONT_FACE
	 * 
	 */
	private static final String DEFAULT_FONT_FACE = "fonts/Roboto_Light.ttf";

	/**
	 * Constructor
	 */
	private FontUtils () {
	}

	/**
	 * @author Walter Hugo Palladino
	 * @since 13/09/2013
	 * 
	 */
	private enum FontType {
		BOLD("fonts/Roboto/Roboto-BoldCondensed.ttf"), BOLD_ITALIC("fonts/Roboto/Roboto-BoldCondensedItalic.ttf"), NORMAL(
				"fonts/Roboto/Roboto-Condensed.ttf"), ITALIC("fonts/Roboto/Roboto-CondensedItalic.ttf");

		private final String path;

		FontType (String path) {
			this.path = path;
		}

		public String getPath() {
			return path;
		}
	}

	/**
	 * Property Name : typefaceCache
	 * 
	 */
	private static Map <FontType, Typeface> typefaceCache = new EnumMap <FontType, Typeface> (FontType.class);

	/**
	 * getRobotoTypeface : Creates Roboto typeface and puts it into cache
	 * 
	 * @param context
	 * @param fontType
	 * @return
	 */
	private static Typeface getRobotoTypeface(Context context, FontType fontType) {
		String fontPath = fontType.getPath ();

		if (!typefaceCache.containsKey (fontType)) {
			typefaceCache.put (fontType, Typeface.createFromAsset (context.getAssets (), fontPath));
		}

		return typefaceCache.get (fontType);
	}

	/**
	 * getRobotoTypeface : Gets roboto typeface according to passed typeface
	 * style settings.
	 * 
	 * @param context
	 * @param originalTypeface
	 * @return
	 */
	private static Typeface getRobotoTypeface(Context context, Typeface originalTypeface) {
		FontType robotoFontType = null;

		if (originalTypeface == null) {
			robotoFontType = FontType.NORMAL;
		} else {
			int style = originalTypeface.getStyle ();

			switch (style) {
			case Typeface.BOLD:
				robotoFontType = FontType.BOLD;
				break;

			case Typeface.BOLD_ITALIC:
				robotoFontType = FontType.BOLD_ITALIC;
				break;

			case Typeface.ITALIC:
				robotoFontType = FontType.ITALIC;
				break;

			case Typeface.NORMAL:
				robotoFontType = FontType.NORMAL;
				break;
			}
		}

		return (robotoFontType == null) ? originalTypeface : getRobotoTypeface (context, robotoFontType);
	}

	/**
	 * Walks ViewGroups, finds TextViews and applies Typefaces taking styling in
	 * consideration
	 * 
	 * @param context
	 *            - to reach assets
	 * @param view
	 *            - root view to apply typeface to
	 */
	public static void setRobotoFont(Context context, View view) {
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount (); i++) {
				setRobotoFont (context, ((ViewGroup) view).getChildAt (i));
			}
		} else if (view instanceof TextView) {
			Typeface currentTypeface = ((TextView) view).getTypeface ();
			((TextView) view).setTypeface (getRobotoTypeface (context, currentTypeface));
		}
	}

	/**
	 * updateFonts
	 * 
	 * @param context
	 * @param root
	 * @param force
	 */
	public static void updateFonts(Context context, ViewGroup root, boolean force) {
		if (force) {
			updateFonts (context, root);
		} else {
			if (Build.VERSION.SDK_INT < 11) {
				updateFonts (context, root);
			}

		}
	}

	/**
	 * updateFonts
	 * 
	 * @param context
	 * @param root
	 */
	public static void updateFonts(Context context, ViewGroup root) {

		Typeface tf = Typeface.createFromAsset (context.getAssets (), DEFAULT_FONT_FACE);

		for (int i = 0; i < root.getChildCount (); i++) {
			View v = root.getChildAt (i);
			if (v instanceof TextView) {
				((TextView) v).setTypeface (tf);
			} else if (v instanceof EditText) {
				((EditText) v).setTypeface (tf);
			} else if (v instanceof Button) {
				((Button) v).setTypeface (tf);
			} else if (v instanceof android.widget.RelativeLayout) {
				FontUtils.updateFonts (context, (ViewGroup) v);
			} else if (v instanceof android.widget.FrameLayout) {
				FontUtils.updateFonts (context, (ViewGroup) v);
			} else if (v instanceof android.widget.LinearLayout) {
				FontUtils.updateFonts (context, (ViewGroup) v);
			} else if (v instanceof android.widget.ScrollView) {
				FontUtils.updateFonts (context, (ViewGroup) v);
			}

			/*
			 * else if(v instanceof ViewGroup) { changeFonts((ViewGroup)v); }
			 */
		}
	}
}
