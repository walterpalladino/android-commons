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
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;

/**
 * @author Walter Hugo Palladino
 * @since 23/10/2014
 *
 */
public class AnimatedButton extends ImageButton implements View.OnClickListener {

	private static final int ANIM_DURATION_OPEN_TIME = 300;
	private static final int ANIM_DURATION_CLOSE_TIME = 100;

	public static final int BUTTON_STATUS_OPEN = 0;
	public static final int BUTTON_STATUS_CLOSED = 1;

	private int status;
	private boolean animating;

	private Animation openAnimation;
	private Animation closeAnimation;

	private AnimatedButtonListenerCallback mListener;

	/**
	 * @param context
	 */
	public AnimatedButton (Context context) {
		super(context);
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public AnimatedButton (Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public AnimatedButton (Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	/**
	 * init
	 *
	 * @param context
	 */
	private void init (Context context) {

		setAnimations();

		status = BUTTON_STATUS_CLOSED;

		this.setClickable(true);
		setOnClickListener(this);

	}

	/**
	 * setAnimations
	 *
	 */
	private void setAnimations () {

		openAnimation = new RotateAnimation(//
				0,//
				-180,//
				Animation.RELATIVE_TO_SELF, 0.5f, //
				Animation.RELATIVE_TO_SELF, 0.5f //
		);

		Interpolator interpolator = new AccelerateInterpolator(1f);

		openAnimation.setDuration(ANIM_DURATION_OPEN_TIME);
		openAnimation.setFillAfter(true);
		openAnimation.setInterpolator(interpolator);
		openAnimation.setAnimationListener(animationListener);

		closeAnimation = new RotateAnimation(//
				0,//
				180,//
				Animation.RELATIVE_TO_SELF, 0.5f, //
				Animation.RELATIVE_TO_SELF, 0.5f //
		);
		closeAnimation.setDuration(ANIM_DURATION_CLOSE_TIME);
		closeAnimation.setInterpolator(interpolator);
		closeAnimation.setAnimationListener(animationListener);

	}

	/**
	 * animationListener
	 */
	private AnimationListener animationListener = new AnimationListener() {

		@Override
		public void onAnimationStart (Animation animation) {
		}

		@Override
		public void onAnimationEnd (Animation animation) {
			animating = false;
			if (getStatus() == BUTTON_STATUS_OPEN) {
				if (mListener != null) {
					mListener.onButtonOpened();
				}
			} else {
				if (mListener != null) {
					mListener.onButtonClosed();
				}
			}

		}

		@Override
		public void onAnimationRepeat (Animation animation) {
		}

	};

	@Override
	public void onClick (View v) {

		System.out.println("onClick");
		System.out.println("getStatus() : " + getStatus());

		if (!animating) {
			if (mListener != null) {
				mListener.onButtonClicked(v);
			}
			updateButton();
		}

	}

	/**
	 * updateButton
	 *
	 */
	private void updateButton () {
		animating = true;
		if (getStatus() == BUTTON_STATUS_CLOSED) {
			startAnimation(openAnimation);
			this.status = BUTTON_STATUS_OPEN;
		} else {
			startAnimation(closeAnimation);
			this.status = BUTTON_STATUS_CLOSED;
		}

	}

	/**
	 * @return the status
	 */
	public int getStatus () {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus (int status) {
		if (status != this.status) {
			updateButton();
		}
	}

	/**
	 * @author Walter Hugo Palladino
	 * @since 23/10/2014
	 * 
	 */
	public interface AnimatedButtonListenerCallback {
		void onButtonClicked (View v);

		void onButtonOpened ();

		void onButtonClosed ();
	}

	/**
	 * setListener
	 * 
	 * @param listener
	 */
	public void setListener (AnimatedButtonListenerCallback listener) {
		mListener = listener;
	}

}
