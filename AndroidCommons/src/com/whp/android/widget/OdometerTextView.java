package com.whp.android.widget;

import java.util.Locale;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * 
 * @author Walter Hugo Palladino
 * @since Apr 1, 2015
 *
 */
public class OdometerTextView extends TextView {

	private ValueAnimator scrollAnimation;

	private int animationDuration = 250;
	private int animationSteps = 50;

	public static final float IDEAL_ASPECT_RATIO = 1.66f;

	private int value = 0;

	private Paint textPaint;
	private Paint backgroundPaint;

	private int valueAbove;
	private int valueBelow;
	private String valueAboveString;
	private String valueBelowString;

	private int actualAnimValue = 0;

	private float currentValue;
	private float stepValue;
	private float nextValue;

	private int maxDigits = 6;
	private int actualDigit = 0;
	private String digitAboveString;
	private String digitBelowString;

	private Rect textBounds = new Rect();

	public OdometerTextView (Context context) {
		super(context);
		init();
	}

	public OdometerTextView (Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init () {

		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(getTextColors().getDefaultColor());

		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setStyle(Paint.Style.FILL);
		backgroundPaint.setColor(Color.TRANSPARENT);

	}

	@Override
	protected void onDraw (Canvas canvas) {

		// super.onDraw(canvas);

		// Fill the background
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), backgroundPaint);

		// Draw the text
		// textPaint.setTextSize(canvas.getHeight() - getTextPadding() * 2);

		float textSize = canvas.getHeight() - getTextPadding() * 2;// 48.0f;

		if ((scrollAnimation == null) || (!scrollAnimation.isRunning())) {

			valueAboveString = String.format(Locale.getDefault(), "%d", value);
			drawString(canvas, textPaint, textSize, valueAboveString);

		} else {

			textPaint.setTextSize(textSize);

			// Above
			valueAbove = (int) currentValue;
			// Below
			valueBelow = (int) nextValue;

			// Create both Strings
			String tmp = String.format(Locale.getDefault(), "%d", valueAbove);
			valueAboveString = String.format(Locale.getDefault(), "%6s", tmp);

			tmp = String.format(Locale.getDefault(), "%d", valueBelow);
			valueBelowString = String.format(Locale.getDefault(), "%6s", tmp);

			// Iterate from the last value to the first
			for (int digit = maxDigits - 1; digit >= 0; digit--) {

				// String digitAboveString = Character.toString(valueAboveString.charAt(maxDigits - digit - 1));
				// String digitBelowString = Character.toString(valueBelowString.charAt(maxDigits - digit - 1));
				digitAboveString = Character.toString(valueAboveString.charAt(maxDigits - digit - 1));
				digitBelowString = Character.toString(valueBelowString.charAt(maxDigits - digit - 1));

				// If the digit is actual digit
				if ((digit == actualDigit) && (digitAboveString != digitBelowString)) {
					// Animate the digit
					animateDigit(canvas, textPaint, digit, textSize, digitAboveString, digitBelowString);

				} else if (digit > actualDigit) {
					// Draw the digit Above (not already updated)
					drawDigit(canvas, textPaint, digit, textSize, digitAboveString);
				} else {
					// Draw the digit Below (already updated)
					drawDigit(canvas, textPaint, digit, textSize, digitBelowString);
				}
			}

		}

	}

	private void drawString (Canvas canvas, Paint textPaint, float textSize, String string) {

		textPaint.setTextSize(textSize);

		textPaint.getTextBounds("0", 0, 1, textBounds);

		// Measure a text
		textPaint.getTextBounds(string, 0, 1, textBounds);
		float textWidth = textPaint.measureText(string);

		canvas.drawText(string, canvas.getWidth() - textWidth, textSize, textPaint);

	}

	private void animateDigit (Canvas canvas, Paint textPaint, int digit, float textSize, String digitAboveString,
			String digitBelowString) {

		float offset = (float) (actualAnimValue) / 100.0f;
		float offsetY = offset * canvas.getHeight();

		// Above
		textPaint.getTextBounds(digitAboveString, 0, 1, textBounds);

		textPaint.getTextBounds("0", 0, 1, textBounds);
		float digitWidth = textPaint.measureText("0");

		canvas.drawText(digitAboveString, canvas.getWidth() - digitWidth * (digit + 1), textSize - offsetY, textPaint);

		// Below
		textPaint.getTextBounds(digitBelowString, 0, 1, textBounds);

		canvas.drawText(digitBelowString, canvas.getWidth() - digitWidth * (digit + 1), textSize + canvas.getHeight() - offsetY,
				textPaint);

	}

	private void drawDigit (Canvas canvas, Paint textPaint, int digit, float textSize, String digitString) {

		textPaint.setTextSize(textSize);

		textPaint.getTextBounds("0", 0, 1, textBounds);
		float digitWidth = textPaint.measureText("0");

		// Measure a text
		textPaint.getTextBounds("000000", 0, 1, textBounds);

		canvas.drawText(digitString, canvas.getWidth() - digitWidth * (digit + 1), textSize, textPaint);

	}

	public static final int BASE_PADDING_IN_DP = 8;

	private float getTextPadding () {
		return BASE_PADDING_IN_DP * getResources().getDisplayMetrics().density;
	}

	public void setValue (Integer newValue, boolean doAnimation) {

		stepValue = (newValue - this.value) / 1.0f;

		currentValue = value;
		nextValue = currentValue + stepValue;

		this.value = newValue;

		if (doAnimation) {
			startAnimation();
		} else {
			invalidate();
		}
	}

	private void startAnimation () {

		actualDigit = 0;

		scrollAnimation = ValueAnimator.ofInt(0, animationSteps);
		scrollAnimation.setDuration(animationDuration);
		scrollAnimation.setInterpolator(new LinearInterpolator());
		scrollAnimation.setRepeatCount(ValueAnimator.INFINITE);
		scrollAnimation.setRepeatMode(ValueAnimator.RESTART);

		scrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate (ValueAnimator valueAnimator) {
				actualAnimValue = (Integer) valueAnimator.getAnimatedValue();
				invalidate();
			}
		});

		scrollAnimation.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationRepeat (Animator animation) {

/*				if (actualDigit < (maxDigits - 1)) {
					if (digitAboveString.equals(digitBelowString)) {
						actualDigit++;
					} else {
						currentValue += Math.pow(10, actualDigit);
					}
				} else {
					scrollAnimation.end();
				}*/
		    	if (actualDigit<(maxDigits-1)) {
		    		
					digitAboveString = Character.toString(valueAboveString.charAt(maxDigits - actualDigit - 1));
					digitBelowString = Character.toString(valueBelowString.charAt(maxDigits - actualDigit - 1));

		    		if (digitAboveString.equals(digitBelowString)) {
			    		actualDigit++;
		    		} else {
		    			if (valueAbove < valueBelow) {
			    			currentValue += Math.pow(10, actualDigit);
		    			} else {
			    			currentValue -= Math.pow(10, actualDigit);
		    			}
		    		}
		    	} else {
		    		scrollAnimation.end();
		    	}
			}

		});

		scrollAnimation.start();

	}

}
