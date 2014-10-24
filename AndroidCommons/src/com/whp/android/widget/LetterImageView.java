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
package com.whp.android.widget;

import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Walter Hugo Palladino
 * @since 20/10/2014
 *
 *        Allows to set a string to an ImageView so a background could be painted and drawing the first letter inside it
 */
public class LetterImageView extends ImageView {

	public static final int BASE_PADDING_IN_DP = 8;

	private String textString;
	private Paint textPaint;
	private Paint backgroundPaint;
	private int textColor = Color.WHITE;
	private Rect textBounds = new Rect();

	/**
	 * @param context
	 */
	public LetterImageView (Context context) {
		super(context);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LetterImageView (Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public LetterImageView (Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * init
	 *
	 */
	private void init () {
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(textColor);
		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setColor(getBackgroundColor());
	}

	/**
	 * getBackgroundColor
	 *
	 * @return
	 */
	private int getBackgroundColor () {

		int color = 0;

		if ((textString != null) && (textString.length() > 0)) {

			String hexColor;

			byte[] b = textString.getBytes();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				// hexString.append(Integer.toHexString(0xFF & (b[i]+48)));
				hexString.append(Integer.toHexString(0xFF & ((b[i] - 48) * 2 + 48)));
			}
			hexColor = "#" + hexString.substring(0, 6);

			color = Color.parseColor(hexColor);

		} else {
			color = Color.TRANSPARENT;
		}

		return color;
	}

	/**
	 * getTextPadding
	 *
	 * @return
	 */
	private float getTextPadding () {
		return BASE_PADDING_IN_DP * getResources().getDisplayMetrics().density;
	}

	@Override
	protected void onDraw (Canvas canvas) {

		super.onDraw(canvas);

		if ((textString != null) && (textString.length() > 0)) {

			// Get the capitalized first letter
			char letter = textString.toUpperCase(Locale.getDefault()).charAt(0);

			// Set a text font size based on the height of the view
			textPaint.setTextSize(canvas.getHeight() - getTextPadding() * 2);

			// Fill the background
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

			// Measure a text
			textPaint.getTextBounds(String.valueOf(letter), 0, 1, textBounds);
			float textWidth = textPaint.measureText(String.valueOf(letter));
			float textHeight = textBounds.height();

			// Draw the letter
			canvas.drawText(String.valueOf(letter), canvas.getWidth() / 2f - textWidth / 2f, canvas.getHeight() / 2f + textHeight
					/ 2f, textPaint);
		}
	}

	/**
	 * @return the textString
	 */
	public String getTextString () {
		return textString;
	}

	/**
	 * @param textString
	 *            the textString to set
	 */
	public void setTextString (String textString) {
		this.textString = textString;
		init();
		invalidate();
	}

}
