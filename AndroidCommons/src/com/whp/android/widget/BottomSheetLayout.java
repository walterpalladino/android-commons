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

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;

/**
 * @author Walter Hugo Palladino
 * @since 21/10/2014
 *
 */
public class BottomSheetLayout extends LinearLayout implements OnClickListener {

	private Animation inAnimation;
	private Animation outAnimation;

	private static final int ANIM_DURATION_IN_TIME = 300;
	private static final int ANIM_DURATION_OUT_TIME = 100;

	private static final int MIN_HEIGHT_DP = 64;
	private DisplayMetrics dm;

	private boolean needRefresh;

	/**
	 * @param context
	 */
	public BottomSheetLayout (Context context) {
		super(context);
		init(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public BottomSheetLayout (Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	// TODO : Remove for API >= 11
//	public BottomSheetLayout (Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		init(context, attrs);
//	}

	/**
	 * init
	 *
	 */
	private void init (Context context, AttributeSet attrs) {

		this.dm = context.getResources().getDisplayMetrics();

		this.setMinimumHeight((int) (MIN_HEIGHT_DP * dm.density));

		this.setClickable(true);

		ViewTreeObserver observer = getViewTreeObserver();

		// Listen to height changes
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout () {

				if (needRefresh) {
					if (inAnimation == null) {
						setAnimations();
					}
					if (getVisibility() == VISIBLE) {
						startAnimation(inAnimation);
						needRefresh = false;
					} else if ((getVisibility() == INVISIBLE) || (getVisibility() == GONE)) {
						startAnimation(outAnimation);
						needRefresh = false;
					}
				}
			}
		});
	}

	@Override
	public void setVisibility (int visibility) {

		if (getVisibility() != visibility) {
			if (visibility == VISIBLE) {
				needRefresh = true;
			} else if ((visibility == INVISIBLE) || (visibility == GONE)) {
				needRefresh = true;
			}
		}

		super.setVisibility(visibility);
	}

	/**
	 * setAnimations
	 *
	 */
	private void setAnimations () {

		boolean topAligned = true;
		ViewGroup.LayoutParams p = BottomSheetLayout.this.getLayoutParams();
		if (p instanceof LinearLayout.LayoutParams) {
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) p;
			if (lp.gravity == Gravity.BOTTOM) {
				topAligned = false;
			}
		} else if (p instanceof TableRow.LayoutParams) {
			TableRow.LayoutParams lp = (TableRow.LayoutParams) p;
			if (lp.gravity == Gravity.BOTTOM) {
				topAligned = false;
			}
		} else if (p instanceof FrameLayout.LayoutParams) {
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) p;
			if (lp.gravity == Gravity.BOTTOM) {
				topAligned = false;
			}
		}

		float pivotPointYScaling = 1f;
		if (topAligned) {
			pivotPointYScaling = 0f;
		}

		inAnimation = new ScaleAnimation(//
				1f, 1f, // Start and end values for the X axis scaling
				0, 1f, // Start and end values for the Y axis scaling
				Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
				Animation.RELATIVE_TO_SELF, pivotPointYScaling // Pivot point of Y scaling
		);

		Interpolator interpolator = new AccelerateInterpolator(1f);

		inAnimation.setDuration(ANIM_DURATION_IN_TIME);
		inAnimation.setFillAfter(true);
		inAnimation.setInterpolator(interpolator);

		outAnimation = new ScaleAnimation(//
				1f, 1f, // Start and end values for the X axis scaling
				1f, 0, // Start and end values for the Y axis scaling
				Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
				Animation.RELATIVE_TO_SELF, pivotPointYScaling // Pivot point of Y scaling
		);
		outAnimation.setDuration(ANIM_DURATION_OUT_TIME);
		outAnimation.setInterpolator(interpolator);

	}

	// Filter
	@Override
	public void onClick (View v) {
	}

}
