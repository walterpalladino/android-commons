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
package com.whp.android.bitmap.cache;

import java.io.UnsupportedEncodingException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * 
 * @author Walter Hugo Palladino
 * @since Mar 2, 2015
 *
 */
public enum BitmapMemoryCache {

	INSTANCE;

	private final Map<String, SoftReference<Bitmap>> cache;

	/**
	 * 
	 */
	BitmapMemoryCache () {
		cache = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * 
	 * put
	 *
	 * @param key
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean put (String url, Bitmap bitmap) throws UnsupportedEncodingException {
		String key = URLEncoder.encode(url, "UTF-8");
		cache.put(key, new SoftReference<Bitmap>(bitmap));
		return true;
	}

	/**
	 * 
	 * get
	 *
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Bitmap get (String url) throws UnsupportedEncodingException {
		String key = URLEncoder.encode(url, "UTF-8");
		if (cache.containsKey(key)) {
			return cache.get(key).get();
		}
		return null;
	}

	/**
	 * 
	 * delete
	 *
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Bitmap delete (String url) throws UnsupportedEncodingException {
		String key = URLEncoder.encode(url, "UTF-8");
		Reference<Bitmap> bmpRef = cache.remove(key);
		return bmpRef == null ? null : bmpRef.get();
	}

	/**
	 * 
	 * clear
	 *
	 */
	public void clear () {
		cache.clear();
	}

}
