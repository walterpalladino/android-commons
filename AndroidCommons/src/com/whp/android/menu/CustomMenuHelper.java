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

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * @author Walter Hugo Palladino
 * @since 12/07/2013
 * 
 */
public class CustomMenuHelper {

	private List <CustomMenuItem> menuItems;
	private View view;
	private Integer menuId;

	private ListView listViewSlidingMenu;

	private int listViewSlidingMenuId;
	private int slideMenuRowId;

	protected CustomMenuSelectListener mListener;

	/**
	 * Constructor
	 * 
	 * @param view
	 * @param menuId
	 * @param listViewSlidingMenuId
	 * @param slideMenuRowId
	 */
	public CustomMenuHelper (View view, Integer menuId, int listViewSlidingMenuId, int slideMenuRowId) {
		this.view = view;
		this.menuId = menuId;
		this.listViewSlidingMenuId = listViewSlidingMenuId;
		this.slideMenuRowId = slideMenuRowId;
	}

	/**
	 * fillMenuData
	 */
	public void fillMenuData() {

		CustomMenuItemAdapter dataAdapter;

		listViewSlidingMenu = (ListView) view.findViewById (this.listViewSlidingMenuId);

		menuItems = CustomMenuParser.parseXml (menuId);

		if (menuItems != null) {
			dataAdapter = new CustomMenuItemAdapter (view.getContext (), slideMenuRowId, menuItems);

			listViewSlidingMenu.setAdapter (dataAdapter);

			listViewSlidingMenu.setOnItemClickListener (listViewSlidingMenuOnItemClickListener);
		}
	}

	/**
	 * 
	 */
	ListView.OnItemClickListener listViewSlidingMenuOnItemClickListener = new ListView.OnItemClickListener () {

		@Override
		public void onItemClick(AdapterView <?> adapter, View v, int position, long id) {
			CustomMenuItem menuItem = (CustomMenuItem) adapter.getAdapter ().getItem (position);
			mListener.onMenuClicked (position, menuItem.getActionClass ());
		}

	};

	public void setListener(CustomMenuSelectListener l) {
		mListener = l;
	}

	/**
	 * @author Walter Hugo Palladino
	 * @since 12/07/2013
	 * 
	 */
	public interface CustomMenuSelectListener {

		public void onMenuClicked(int position, String className);

	}

}
