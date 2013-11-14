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
import java.util.Locale;

/**
 * @author Walter Hugo Palladino
 * @since 12/10/2013
 * 
 */
public class RssPost implements Serializable {

	/**
	 * Property Name : serialVersionUID
	 * 
	 */
	private static final long serialVersionUID = 4150348911582777782L;

	/**
	 * Property Name : title
	 * 
	 */
	private String title;
	/**
	 * Property Name : thumbnail
	 * 
	 */
	private String thumbnail;
	/**
	 * Property Name : url
	 * 
	 */
	private String url;
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
	 * Property Name : author
	 * 
	 */
	private String author;
	/**
	 * Property Name : creator
	 * 
	 */
	private String creator;
	/**
	 * Property Name : content
	 * 
	 */
	private String content;

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
	 * getThumbnail
	 * 
	 * @return
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * setThumbnail
	 * 
	 * @param thumbnail
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * getUrl
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * setUrl
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * getAuthor
	 * 
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * setAuthor
	 * 
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
