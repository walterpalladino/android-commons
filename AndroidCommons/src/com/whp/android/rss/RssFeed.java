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
package com.whp.android.rss;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Walter Hugo Palladino
 * @since 12/10/2013
 * 
 */
public class RssFeed implements Serializable {

	/**
	 * Property Name : serialVersionUID
	 * 
	 */
	private static final long serialVersionUID = -231353568413461793L;

	/**
	 * Property Name : title
	 * 
	 */
	private String title;
	/**
	 * Property Name : description
	 * 
	 */
	private String description;
	/**
	 * Property Name : pubDate
	 * 
	 */
	private Date pubDate;
	/**
	 * Property Name : categories
	 * 
	 */
	private List <String> categories;
	/**
	 * Property Name : rssItems
	 * 
	 */
	private List <RssPost> rssItems;

	/**
	 * getTitle
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * setTitle
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * setDescription
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * getDescription
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * setPubDate
	 * 
	 * @param pubDate
	 */
	public void setPubDate(String pubDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat ("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		try {
			this.pubDate = dateFormat.parse (pubDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace ();
		}

	}

	/**
	 * getPubDate
	 * 
	 * @return
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * getCategories
	 * 
	 * @return
	 */
	public List <String> getCategories() {
		return categories;
	}

	/**
	 * setCategories
	 * 
	 * @param categories
	 */
	public void setCategories(List <String> categories) {
		this.categories = categories;
	}

	/**
	 * getRssItems
	 * 
	 * @return
	 */
	public List <RssPost> getRssItems() {
		return rssItems;
	}

	/**
	 * setRssItems
	 * 
	 * @param rssItems
	 */
	public void setRssItems(List <RssPost> rssItems) {
		this.rssItems = rssItems;
	}

}
