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
package com.whp.android.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

/**
 * @author Walter Hugo Palladino 
 * Inspired and modified from stackoverflow http://stackoverflow.com/questions/13005101/how-to-send-json-data-to-post-restful-service
 * @since 21/03/2014
 * 
 */
/*
 * RestClient client=new RestClient(Webservices.student_details);
JSONObject obj=new JSONObject();
obj.put("StudentId",preferences.getStudentId());
client.AddParam("",obj.toString());
client.Execute(RequestMethod.GET);
String response=client.getResponse();
 */
public class RestClient {

	private static final int CONNECTION_TIMEOUT	= 15000;
	
	private ArrayList <NameValuePair> params;
	private ArrayList <NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RestClient (String url) {
		this.url = url;
		params = new ArrayList <NameValuePair> ();
		headers = new ArrayList <NameValuePair> ();
	}

	public void addParam(String name, String value) {
		params.add (new BasicNameValuePair (name, value));
	}

	public void addHeader(String name, String value) {
		headers.add (new BasicNameValuePair (name, value));
	}

	/**
	 * Execute
	 * @param method
	 * @throws Exception
	 */
	public void execute(RequestMethod method) throws Exception {
		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			if (!params.isEmpty ()) {
				combinedParams += "";
				for (NameValuePair p : params) {
					String paramString = p.getName () + "" + URLEncoder.encode (p.getValue (), "UTF-8");
					if (combinedParams.length () > 1) {
						combinedParams += "&" + paramString;
					} else {
						combinedParams += paramString;
					}
				}
			}

			HttpGet request = new HttpGet (url + combinedParams);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader (h.getName (), h.getValue ());
			}

			executeRequest (request, url);
			break;
		}
		case POST: {
			HttpPost request = new HttpPost (url);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader (h.getName (), h.getValue ());
			}

			if (!params.isEmpty ()) {
				request.setEntity (new UrlEncodedFormEntity (params, HTTP.UTF_8));
			}

			executeRequest (request, url);
			break;
		}
		}
	}

	/**
	 * executeRequest
	 * @param request
	 * @param url
	 * @throws Exception
	 */
	private void executeRequest(HttpUriRequest request, String url) throws Exception {

		HttpParams httpParameters = new BasicHttpParams ();
		HttpConnectionParams.setConnectionTimeout (httpParameters, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout (httpParameters, CONNECTION_TIMEOUT);
		HttpClient client = new DefaultHttpClient (httpParameters);

		HttpResponse httpResponse;

		httpResponse = client.execute (request);
		responseCode = httpResponse.getStatusLine ().getStatusCode ();
		message = httpResponse.getStatusLine ().getReasonPhrase ();

		HttpEntity entity = httpResponse.getEntity ();

		if (entity != null) {

			InputStream instream = entity.getContent ();
			response = convertStreamToString (instream);

			// Closing the input stream will trigger connection release
			instream.close ();
		}

	}

	/**
	 * convertStreamToString
	 * @param is
	 * @return
	 */
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader (new InputStreamReader (is));
		StringBuilder sb = new StringBuilder ();

		String line = null;
		try {
			while ((line = reader.readLine ()) != null) {
				sb.append (line + "\n");
			}
		} catch (IOException e) {

			e.printStackTrace ();
		} finally {
			try {
				is.close ();
			} catch (IOException e) {
				e.printStackTrace ();
			}
		}
		return sb.toString ();
	}

	/**
	 * getInputStream
	 * @return
	 */
	public InputStream getInputStream() {
		HttpParams httpParameters = new BasicHttpParams ();
		HttpConnectionParams.setConnectionTimeout (httpParameters, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout (httpParameters, CONNECTION_TIMEOUT);
		HttpClient client = new DefaultHttpClient (httpParameters);

		HttpResponse httpResponse;

		try {

			HttpPost request = new HttpPost (url);

			httpResponse = client.execute (request);
			responseCode = httpResponse.getStatusLine ().getStatusCode ();
			message = httpResponse.getStatusLine ().getReasonPhrase ();

			HttpEntity entity = httpResponse.getEntity ();

			if (entity != null) {

				InputStream instream = entity.getContent ();
				return instream;
				/*
				 * response = convertStreamToString(instream);
				 * 
				 * // Closing the input stream will trigger connection release
				 * instream.close();
				 */
			}

		} catch (ClientProtocolException e) {
			client.getConnectionManager ().shutdown ();
			e.printStackTrace ();
		} catch (IOException e) {
			client.getConnectionManager ().shutdown ();
			e.printStackTrace ();
		}
		return null;
	}

	/**
	 * @author Walter Hugo Palladino
	 * @since 21/03/2014
	 *
	 */
	public enum RequestMethod {
		GET, POST
	}

}
