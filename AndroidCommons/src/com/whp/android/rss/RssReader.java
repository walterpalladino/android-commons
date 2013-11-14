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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class RssReader {


	public HashMap<String, String> userList = new HashMap<String, String>();
	private URL url2;

	
	public RssReader(String url1) throws MalformedURLException {
		this.url2 = new URL(url1);
	}

	public RssFeed parseContent(String parseContent) {
		RSSHandler df = new RSSHandler();
		try {

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(df);
			xr.parse(new InputSource(url2.openStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.getResult();
	}
	
	
}


class RSSHandler extends DefaultHandler {

	private RssPost currentPost ;
	private RssFeed rssFeed ;
	
	StringBuffer chars = new StringBuffer();

	private ArrayList<RssPost> postList = new ArrayList<RssPost>();

	public RssFeed getResult() {
		return rssFeed;
	}

	@Override
	public void startDocument() {
		rssFeed	= new RssFeed ();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) {

		chars = new StringBuffer();
		if (localName.equalsIgnoreCase("item")) {
			currentPost = new RssPost();
		}
		
		if (localName.trim().equals("thumbnail")) {          
			chars = new StringBuffer(atts.getValue("url"));            
	    }
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (currentPost != null) {

			if (localName.equalsIgnoreCase("title")
					&& currentPost.getTitle() == null) {
				currentPost.setTitle(chars.toString());
			}
			
			if (localName.equalsIgnoreCase("creator")
					&& currentPost.getCreator() == null) {
				currentPost.setCreator(chars.toString());
			}

			if (localName.equalsIgnoreCase("description")
					&& currentPost.getDescription() == null) {
				currentPost.setDescription(chars.toString());
			}
			if (localName.equalsIgnoreCase("encoded")
					&& currentPost.getContent () == null) {
				currentPost.setContent(chars.toString());
			}
			if (localName.equalsIgnoreCase("pubDate")
					&& currentPost.getPubDate() == null) {
				currentPost.setPubDate(chars.toString());

			}
			if (localName.equalsIgnoreCase("thumbnail")
					&& currentPost.getThumbnail() == null) {
				currentPost.setThumbnail(chars.toString());

			}
			if (localName.equalsIgnoreCase("link")
					&& currentPost.getUrl() == null) {
				currentPost.setUrl(chars.toString());
			}

			if (localName.equalsIgnoreCase("item")) {
				postList.add(currentPost);
//				currentPost = new RssPost();
			}
			
		} else {
			
			
			if (localName.equalsIgnoreCase("title")
					&& rssFeed.getTitle() == null) {
				rssFeed.setTitle(chars.toString());
			}
			
			if (localName.equalsIgnoreCase("description")
					&& rssFeed.getDescription() == null) {
				rssFeed.setDescription(chars.toString());
			}
			
			if (localName.equalsIgnoreCase("pubDate")
					&& rssFeed.getPubDate() == null) {
				rssFeed.setPubDate(chars.toString());

			}
		}


	}

	@Override
	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}
	
	@Override
	public void endDocument() {
		rssFeed.setRssItems(postList);
	}

}
