
/**
 * Copyright 2013 Marc Schachtel, Germany
 */

package com.cloutree.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

/**
 * HttpSender
 *
 * @author mschachtel
 *
 */

@SuppressWarnings("deprecation")
public class HttpSender {

	public static HttpResponse sendHttpPostJson(String jsonObject, String uriString) {
		
		HttpPost post = new HttpPost();
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);        
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		UrlEncodedFormEntity entity;
		
		postParams.add(new BasicNameValuePair("json", jsonObject));
		try {
			entity = new UrlEncodedFormEntity(postParams);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			return null;
		}
		
		URI uri = null;
		try {
			uri = new URI(uriString);
		} catch (URISyntaxException e) {
			//TODO
			return null;
		}
		
		entity.setContentEncoding(HTTP.UTF_8);
	    entity.setContentType("application/json");
		
		post.setURI(uri);
		post.setEntity(entity);
		
		try {
			return httpClient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
}

