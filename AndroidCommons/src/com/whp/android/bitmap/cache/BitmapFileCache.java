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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Walter Hugo Palladino
 * @since Mar 4, 2015
 *
 */
public enum BitmapFileCache {

	INSTANCE;

	private static final String CACHE_DIR_NAME = "bitmaps_cache";

	// private final Map<String, Bitmap> cache;
	private File cacheDir;

	/**
	 * 
	 */
	BitmapFileCache () {
	}

	public void init (Context context) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), CACHE_DIR_NAME);
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	/**
	 * 
	 * put
	 *
	 * @param url
	 * @param bitmap
	 * @return
	 * @throws IOException
	 */
	public boolean put (String url, Bitmap bitmap) throws IOException {
		File f = getFile(url);

		FileOutputStream out = null;

		out = new FileOutputStream(f);
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		out.close();

		return true;
	}

	/**
	 * 
	 * get
	 *
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public Bitmap get (String url) throws UnsupportedEncodingException, FileNotFoundException {
		File f = getFile(url);
		Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
		return b;
	}

	/**
	 * 
	 * delete
	 *
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean delete (String url) throws UnsupportedEncodingException {
		File f = getFile(url);
		return f.delete();
	}

	/**
	 * 
	 * clear
	 *
	 */
	public void clear () {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

	/**
	 * 
	 * getFile
	 *
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private File getFile (String url) throws UnsupportedEncodingException {
		String fileName = URLEncoder.encode(url, "UTF-8");
		File f = new File(cacheDir, fileName);
		return f;
	}

}
