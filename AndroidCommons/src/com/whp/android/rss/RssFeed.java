package com.whp.android.rss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RssFeed {

	
	private String title;
	private String description;
	private Date pubDate;
	private List<String>	categories;
	private List<RssPost>	rssItems;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPubDate(String pubDate) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		try {
			this.pubDate = dateFormat.parse(pubDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Date getPubDate() {
		return pubDate;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<RssPost> getRssItems() {
		return rssItems;
	}

	public void setRssItems(List<RssPost> rssItems) {
		this.rssItems = rssItems;
	}

	
}
