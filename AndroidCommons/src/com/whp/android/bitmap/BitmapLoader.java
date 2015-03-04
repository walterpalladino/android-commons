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
package com.whp.android.bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Walter Hugo Palladino
 * @since Mar 2, 2015
 *
 */
public class BitmapLoader {

	/**
	 * 
	 * downloadBitmap
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static Bitmap downloadBitmap (String url) throws IOException   {
		// URL urlUrl = new URL(url);
		// TODO : Remove
		// InputStream content = (InputStream) urlUrl.getContent();

//		 Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
		
//		 return bitmap;

//		URL url = new URL(imageUrl);
//		HttpGet httpRequest = null;
//
//		httpRequest = new HttpGet(url.toURI());
//
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
//
//		HttpEntity entity = response.getEntity();
//		BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
//		InputStream input = b_entity.getContent();
//
//		Bitmap bitmap = BitmapFactory.decodeStream(input);
//		input.close();
//
//		return bitmap;
		
		System.gc();
		
		//BitmapFactory.Options options = new BitmapFactory.Options ();
        //options.inSampleSize = 4;

//		URL urlUrl = new URL(url);
//		InputStream is = (InputStream) urlUrl.getContent();
//		//return BitmapFactory.decodeStream(new FlushedInputStream(is), null, options);
//		return BitmapFactory.decodeStream(new FlushedInputStream(is));

		HttpGet httpRequest = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = (HttpResponse) httpclient
		                    .execute(httpRequest);
		HttpEntity entity = response.getEntity();
		BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
		InputStream is = bufferedHttpEntity.getContent();
		
		return BitmapFactory.decodeStream(is);
	
	}
}
