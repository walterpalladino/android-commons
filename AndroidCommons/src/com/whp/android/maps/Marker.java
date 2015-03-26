package com.whp.android.maps;

import com.whp.android.graphic.Pixel;
import com.whp.android.location.LocationData;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * @author Walter Hugo Palladino
 * @since Feb 22, 2015
 *
 */
public class Marker {

	public static final int BACKGROUND_STYLE_NONE = 0;
	public static final int BACKGROUND_STYLE_CIRCLE = 1;
	public static final int BACKGROUND_STYLE_SQUARE = 2;

	private int id;
	private int customType;

	private String name;
	private Bitmap image;

	// Latitude and Longitude
	private LocationData coordinate = new LocationData();
	// Location in the world as Mercator projection
	private Pixel pixel = new Pixel();
	// Screen Location after transformations to fit in screen
	private Pixel screen = new Pixel();

	private Color color;
	private int backgroundColor;
	private int backgroundStyle;
	private Drawable drawable;

	private int width;
	private int height;

	private int offsetX;
	private int offsetY;

	public Marker () {

		id = -1;
		customType = 1;

		width = 32;
		height = 32;

		offsetX = 0;
		offsetY = 0;

		backgroundStyle = BACKGROUND_STYLE_NONE;
		backgroundColor = Color.TRANSPARENT;

	}

	public int getId () {
		return id;
	}

	public void setId (int id) {
		this.id = id;
	}

	public int getCustomType () {
		return customType;
	}

	public void setCustomType (int customType) {
		this.customType = customType;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public Bitmap getImage () {
		return image;
	}

	public void setImage (Bitmap image) {
		this.image = image;
	}

	public LocationData getCoordinate () {
		return coordinate;
	}

	public void setCoordinate (LocationData coordinate) {
		this.coordinate = coordinate;
	}

	public Pixel getPixel () {
		return pixel;
	}

	public void setPixel (Pixel pixel) {
		this.pixel = pixel;
	}

	public Pixel getScreen () {
		return screen;
	}

	public void setScreen (Pixel screen) {
		this.screen = screen;
	}

	public Color getColor () {
		return color;
	}

	public void setColor (Color color) {
		this.color = color;
	}

	public Drawable getDrawable () {
		return drawable;
	}

	public void setDrawable (Drawable drawable) {
		this.drawable = drawable;
	}

	public int getWidth () {
		return width;
	}

	public void setWidth (int width) {
		this.width = width;
	}

	public int getHeight () {
		return height;
	}

	public void setHeight (int height) {
		this.height = height;
	}

	public int getOffsetX () {
		return offsetX;
	}

	public void setOffsetX (int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY () {
		return offsetY;
	}

	public void setOffsetY (int offsetY) {
		this.offsetY = offsetY;
	}

	public int getBackgroundColor () {
		return backgroundColor;
	}

	public void setBackgroundColor (int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getBackgroundStyle () {
		return backgroundStyle;
	}

	public void setBackgroundStyle (int backgroundStyle) {
		this.backgroundStyle = backgroundStyle;
	}

}
