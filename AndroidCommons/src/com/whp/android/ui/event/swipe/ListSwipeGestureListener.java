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
package com.whp.android.ui.event.swipe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

/**
 * @author Walter Hugo Palladino
 * @since 20/10/2014
 *
 */
public class ListSwipeGestureListener extends SimpleOnGestureListener implements OnTouchListener {

	@SuppressWarnings("unused")
	private Context context;
	private GestureDetector gestureDetector;
	private ListView listView = null;

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private ListSwipeGestureListenerCallback mListener;

	/**
	 * 
	 */
	public ListSwipeGestureListener () {
		super();
	}

	/**
	 * @param context
	 */
	public ListSwipeGestureListener (Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param gestureDetector
	 */
	public ListSwipeGestureListener (Context context, GestureDetector gestureDetector) {
		this(context, gestureDetector, null);
	}

	/**
	 * @param context
	 * @param gestureDetector
	 * @param listView
	 */
	public ListSwipeGestureListener (Context context, GestureDetector gestureDetector, ListView listView) {

		if (gestureDetector == null)
			gestureDetector = new GestureDetector(context, this);

		this.context = context;
		this.gestureDetector = gestureDetector;
		this.listView = listView;
	}

	@Override
	public boolean onFling (MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

		int position = -1;
		if (listView != null) {
			position = listView.pointToPosition(Math.round(e1.getX()), Math.round(e1.getY()));
		}

		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
			if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH || Math.abs(velocityY) < SWIPE_THRESHOLD_VELOCITY) {
				return false;
			}
			if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
				if (mListener != null) {
					mListener.onListViewSwipeUp();
				}
			} else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
				if (mListener != null) {
					mListener.onListViewSwipeDown();
				}
			}
		} else {
			if (Math.abs(velocityX) < SWIPE_THRESHOLD_VELOCITY) {
				return false;
			}
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {

				if (mListener != null) {
					mListener.onListViewSwipeLeft(position);
				}
				return true;
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {

				if (mListener != null) {
					mListener.onListViewSwipeRight(position);
				}
				return true;
			}
		}

		return super.onFling(e1, e2, velocityX, velocityY);

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch (View v, MotionEvent event) {
		//System.out.println("onTouch");
		return gestureDetector.onTouchEvent(event);
	}

	/**
	 * getDetector
	 *
	 * @return
	 */
	public GestureDetector getDetector () {
		return gestureDetector;
	}

	/**
	 * @author Walter Hugo Palladino
	 * @since 21/10/2014
	 * 
	 */
	public interface ListSwipeGestureListenerCallback {

		void onListViewSwipeRight (int position);

		void onListViewSwipeLeft (int position);

		void onListViewSwipeUp ();

		void onListViewSwipeDown ();

	}

	/**
	 * setListener
	 * 
	 * @param listener
	 */
	public void setListener (ListSwipeGestureListenerCallback listener) {
		mListener = listener;
	}

}
