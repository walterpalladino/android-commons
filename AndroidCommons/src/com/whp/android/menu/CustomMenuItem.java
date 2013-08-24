package com.whp.android.menu;

/**
 * @author Walter Hugo Palladino
 * @since 12/07/2013
 *
 */
public class CustomMenuItem {
	
	private int id;
	private String caption;
	private int imageId;
	private String actionClass ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getActionClass() {
		return actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

}
