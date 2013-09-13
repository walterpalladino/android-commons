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
package com.whp.android.gallery;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


import com.whp.android.BasicApplication;


public class ExternalImageListHelper {

    // All static variables
    static final String URL = "http://api.androidhive.info/pizza/?format=xml";
    // XML node keys
    static final String KEY_IMAGE = "image"; // parent node
    static final String KEY_URL = "url";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_DATE = "date";
    static final String KEY_AUTHOR = "author";

	
	public List<ExternalImage> parseXml(String externalListFile) {

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp;
        
        ExternalImageListContentHandler df = new ExternalImageListContentHandler();
        
		try {
			
			InputStream istr = BasicApplication.getAppContext().getAssets().open( externalListFile );
			
			sp = spf.newSAXParser();
			
	        XMLReader xmlReader = sp.getXMLReader();
	        xmlReader.setContentHandler( df );
	        xmlReader.parse(new InputSource(istr));
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return df.getResult() ;
		
	}
	
	private class ExternalImageListContentHandler extends DefaultHandler {

		private List<ExternalImage> images	= new ArrayList<ExternalImage>();
		private ExternalImage	image;
		
		private StringBuffer chars = new StringBuffer();
		
		public List<ExternalImage> getResult() {
			return images;
		}
		
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        	chars = new StringBuffer();
        	
            if (localName.equals(KEY_IMAGE)) {
            	image	= new ExternalImage() ;
            }

        }
        
    	@Override
    	public void endElement(String uri, String localName, String qName)
    			throws SAXException {

    		if (image != null) {

    			if (localName.equalsIgnoreCase(KEY_URL)
    					&& image.getUrl() == null) {
    				image.setUrl(chars.toString().replaceAll("\\r\\n|\\r|\\n| ", ""));
    			}

    			if (localName.equalsIgnoreCase(KEY_DESCRIPTION)
    					&& image.getDescription() == null) {
    				image.setDescription(chars.toString());
    			}

    			if (localName.equalsIgnoreCase(KEY_AUTHOR)
    					&& image.getAuthor() == null) {
    				image.setAuthor(chars.toString());
    			}
    			
    			if (localName.equalsIgnoreCase(KEY_DATE)
    					&& image.getCreationDate() == null) {
    				image.setCreationDate(chars.toString());
    			}
    			
    			if (localName.equalsIgnoreCase(KEY_IMAGE)) {
    				images.add(image);
    			}

    		}
    	}
    	
    	@Override
    	public void characters(char ch[], int start, int length) {
    		chars.append(new String(ch, start, length));
    	}
    }
}
