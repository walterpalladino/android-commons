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
package com.whp.android.fx.fireworks.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.whp.android.fx.fireworks.model.Explosion;

/**
 * @author Walter Hugo Palladino
 * @since 17/11/2013
 * 
 */
public class FireworksSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private static final int EXPLOSION_SIZE = 200;

	private MainThread thread;
	private Explosion explosion;

	private int width;
	private int height;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public FireworksSurfaceView (Context context) {
		super (context);

		// adding the callback (this) to the surface holder to intercept events
		getHolder ().addCallback (this);

		setZOrderOnTop (true);

		SurfaceHolder holder = getHolder ();
		holder.setFormat (PixelFormat.TRANSLUCENT);

	}

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param attrs
	 */
	public FireworksSurfaceView (Context context, AttributeSet attrs) {
		super (context, attrs);

		// adding the callback (this) to the surface holder to intercept events
		getHolder ().addCallback (this);

		if(!isInEditMode()) {
		setZOrderOnTop (true);
		}

		SurfaceHolder holder = getHolder ();
		holder.setFormat (PixelFormat.TRANSLUCENT);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// create the game loop thread
		thread = new MainThread (getHolder (), this);

		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning (true);
		thread.start ();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning (false);
				thread.join ();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}

	}

	/**
	 * startExplosions
	 */
	private void startExplosions(int x, int y) {

		// check if explosion is null or if it is still active
		if (explosion == null || explosion.getState () == Explosion.STATE_DEAD) {
			explosion = new Explosion (EXPLOSION_SIZE, x, y);
		}

	}

	/**
	 * render
	 * 
	 * @param canvas
	 */
	public void render(Canvas canvas) {

		// Clear our Transparent Canvas
		canvas.drawColor (0, Mode.CLEAR);

		// render explosions
		renderExplosions (canvas);

	}

	/**
	 * renderExplosions
	 * 
	 * @param canvas
	 */
	private void renderExplosions(Canvas canvas) {

		if (explosion != null) {
			explosion.draw (canvas);
		}

	}

	/**
	 * rndInt
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	static int rndInt(int min, int max) {
		return (int) (min + Math.random () * (max - min + 1));
	}

	/**
	 * update
	 */
	public void update() {

		int x = rndInt (0, width);
		int y = rndInt (0, height / 3);

		startExplosions (x, y);

		// update explosions
		if (explosion != null && explosion.isAlive ()) {
			explosion.update (getHolder ().getSurfaceFrame ());
		}
	}

}
