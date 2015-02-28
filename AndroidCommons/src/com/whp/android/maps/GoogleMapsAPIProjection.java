/*
 * Copyright 2015 Walter Hugo Palladino
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
package com.whp.android.maps;

import com.whp.android.graphic.Pixel;
import com.whp.android.location.LocationData;

/**
 * @author Walter Hugo Palladino
 * @since Feb 28, 2015
 *
 */
public class GoogleMapsAPIProjection {

	/*
	 * Based on Mercator projection
	 */
	private double PixelTileSize = 256d;
	private double DegreesToRadiansRatio = 180d / Math.PI;
	private double RadiansToDegreesRatio = Math.PI / 180d;
	private Pixel PixelGlobeCenter;
	private double XPixelsToDegreesRatio;
	private double YPixelsToRadiansRatio;

	/**
	 * Constructor
	 * 
	 * @param zoomLevel
	 */
	public GoogleMapsAPIProjection (int zoomLevel) {

		double pixelGlobeSize = this.PixelTileSize * Math.pow(2d, zoomLevel);

		this.XPixelsToDegreesRatio = pixelGlobeSize / 360d;
		this.YPixelsToRadiansRatio = pixelGlobeSize / (2d * Math.PI);

		double halfPixelGlobeSize = pixelGlobeSize / 2d;

		this.PixelGlobeCenter = new Pixel(halfPixelGlobeSize, halfPixelGlobeSize);
	}

	/**
	 * Translates from world Coordinate to flat projected Pixel
	 * 
	 * @param coordinates
	 * @return
	 */
	public Pixel fromCoordinatesToPixel (LocationData coordinates) {

		double x = Math.round(this.PixelGlobeCenter.getX() + (coordinates.longitude * this.XPixelsToDegreesRatio));

		double f = Math.min(Math.max(Math.sin(coordinates.latitude * RadiansToDegreesRatio), -0.9999d), 0.9999d);
		double y = Math.round(this.PixelGlobeCenter.getY() + .5d * Math.log((1d + f) / (1d - f)) * -this.YPixelsToRadiansRatio);

		return new Pixel(x, y);
	}

	/**
	 * Translates Pixel to world Coordinate
	 * 
	 * @param pixel
	 * @return
	 */
	public LocationData fromPixelToCoordinates (Pixel pixel) {
		double longitude = (pixel.getX() - this.PixelGlobeCenter.getX()) / this.XPixelsToDegreesRatio;
		double latitude = (2 * Math.atan(Math.exp((pixel.getY() - this.PixelGlobeCenter.getY()) / -this.YPixelsToRadiansRatio)) - Math.PI / 2)
				* DegreesToRadiansRatio;

		return new LocationData(latitude, longitude);
	}

}
