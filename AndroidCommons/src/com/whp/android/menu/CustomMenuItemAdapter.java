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
package com.whp.android.menu;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Walter Hugo Palladino
 * @since 12/07/2013
 *
 */
public class CustomMenuItemAdapter extends ArrayAdapter <CustomMenuItem> {

	private final Context context;
	private final List <CustomMenuItem> values;
	private final int layoutId;

	/**
	 * Constructor
	 * @param context
	 * @param layout
	 * @param values
	 */
	public CustomMenuItemAdapter (Context context, int layout, List <CustomMenuItem> values) {
		super (context, layout, values);
		this.context = context;
		this.values = values;
		this.layoutId = layout;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate (layoutId, parent, false);

		CustomMenuItem menuItem = values.get (position);

		int idItemCaption = this.context.getResources ().getIdentifier ("menu_item_caption", "id",
				getContext ().getPackageName ());
		TextView caption = (TextView) rowView.findViewById (idItemCaption);
		caption.setText (menuItem.getCaption ());

		int idIcon = this.context.getResources ().getIdentifier ("menu_item_icon", "id",
				getContext ().getPackageName ());
		ImageView icon = (ImageView) rowView.findViewById (idIcon);
		icon.setImageResource (menuItem.getImageId ());

		return rowView;
	}

}
