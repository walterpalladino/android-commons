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
package com.whp.android.fx.fireworks.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * @author Walter Hugo Palladino
 * @since 17/11/2013
 *
 */
public class Explosion {

	@SuppressWarnings("unused")
	private static final String TAG = Explosion.class.getSimpleName();

	public static final int STATE_ALIVE = 0; // at least 1 particle is alive
	public static final int STATE_DEAD = 1; // all particles are dead

	private Particle[] particles; // particles in the explosion
	private int x, y; // the explosion's origin
	private float gravity; // the gravity of the explosion (+ upward, - down)
	private float wind; // speed of wind on horizontal
	private int size; // number of particles
	private int state; // whether it's still active or not

	/**
	 * @param particleNr
	 * @param x
	 * @param y
	 */
	public Explosion (int particleNr, int x, int y) {

		int color = Color.argb(255, rndInt(0, 255), rndInt(0, 255), rndInt(0, 255));

		this.state = STATE_ALIVE;
		this.particles = new Particle[particleNr];
		for (int i = 0; i < this.particles.length; i++) {
			Particle p = new Particle(x, y, color);
			this.particles[i] = p;
		}
		this.size = particleNr;
	}

	/**
	 * rndInt
	 * 
	 * Return an integer that ranges from min inclusive to max inclusive.
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	static int rndInt (int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	public Particle[] getParticles () {
		return particles;
	}

	public void setParticles (Particle[] particles) {
		this.particles = particles;
	}

	public int getX () {
		return x;
	}

	public void setX (int x) {
		this.x = x;
	}

	public int getY () {
		return y;
	}

	public void setY (int y) {
		this.y = y;
	}

	public float getGravity () {
		return gravity;
	}

	public void setGravity (float gravity) {
		this.gravity = gravity;
	}

	public float getWind () {
		return wind;
	}

	public void setWind (float wind) {
		this.wind = wind;
	}

	public int getSize () {
		return size;
	}

	public void setSize (int size) {
		this.size = size;
	}

	public int getState () {
		return state;
	}

	public void setState (int state) {
		this.state = state;
	}

	// helper methods -------------------------
	/**
	 * isAlive
	 *
	 * @return
	 */
	public boolean isAlive () {
		return this.state == STATE_ALIVE;
	}

	/**
	 * isDead
	 *
	 * @return
	 */
	public boolean isDead () {
		return this.state == STATE_DEAD;
	}

	/**
	 * update
	 *
	 */
	public void update () {
		if (this.state != STATE_DEAD) {
			boolean isDead = true;
			for (int i = 0; i < this.particles.length; i++) {
				if (this.particles[i].isAlive()) {
					this.particles[i].update();
					isDead = false;
				}
			}
			if (isDead)
				this.state = STATE_DEAD;
		}
	}

	/**
	 * update
	 *
	 * @param container
	 */
	public void update (Rect container) {
		if (this.state != STATE_DEAD) {
			boolean isDead = true;
			for (int i = 0; i < this.particles.length; i++) {
				if (this.particles[i].isAlive()) {
					this.particles[i].update(container);
					isDead = false;
				}
			}
			if (isDead)
				this.state = STATE_DEAD;
		}
	}

	/**
	 * draw
	 *
	 * @param canvas
	 */
	public void draw (Canvas canvas) {
		for (int i = 0; i < this.particles.length; i++) {
			if (this.particles[i].isAlive()) {
				this.particles[i].draw(canvas);
			}
		}
	}
}
