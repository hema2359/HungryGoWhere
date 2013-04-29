package com.example.hungry.helper;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


import android.util.Log;


public class HttpHelper {
	
	private static final String TAG="HttpHelper";
	public static final String API_BASE_ADDRESS="http://hgwm2359-staging.herokuapp.com/";
	
	public static String sendRequestUsingGet(String uri,
			ArrayList<NameValuePair> params) throws ClientProtocolException, IOException {
		HttpGet httpGet = null;
	    //-------------Settings for httpClient----------------------------------------//		
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
		//----------------------------------------------------------------------------//	
			String result = "";
			StringBuilder sb = new StringBuilder(uri);
			
			ResponseHandler<String> handler = new ResponseHandler<String>() {
			    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
			       HttpEntity entity = response.getEntity();
		       	   if (entity != null) {
			         return EntityUtils.toString(entity);
			       } else {
			         return null;
			       }
			    }
			};
			
		    if (params != null) {
			   sb.append("?");
			   for (int i = 0; i < params.size(); i++) {
				   if (i > 0)
					    sb.append("&");
				   sb.append( URLEncoder.encode(params.get(i).getName(), "UTF-8"))
								.append("=")
								.append(URLEncoder.encode(params.get(i).getValue(),
										"UTF-8"));
					}
			}
			String requestUri = sb.toString();
			
			httpGet = new HttpGet(requestUri);
			result=httpClient.execute(httpGet,handler);
			//LogUtils.log(" HTTP GET ", result);
			return result;
	}
	
	
	public static String sendRequestUsingPost(HttpPost postRequest) throws Exception{		
	    //-------------Settings for httpClient----------------------------------------//		
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
			HttpConnectionParams.setSoTimeout(httpParams, 15000);
		//----------------------------------------------------------------------------//	
		
		ResponseHandler<String> handler = new ResponseHandler<String>() {
		    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		       HttpEntity entity = response.getEntity();
	       	   if (entity != null) {
		         return EntityUtils.toString(entity);
		       } else {
		         return null;
		       }
		    }
		};
		String response =httpClient.execute(postRequest,handler);
		Log.d("2359", "Response: "+response);
		return response;
	}
	
	
	public static String sendRequestUsingPost(String uri,
			ArrayList<NameValuePair> params) throws Exception {
		HttpPost httpPost = new HttpPost(uri);
		if (params != null)
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		Log.d("2359", "URL: "+uri);
		for (NameValuePair nameValuePair : params) {
			Log.d("2359", "Param: "+nameValuePair.getName()+ " = " +nameValuePair.getValue());
		}
		Log.d("2359", "----------");
		return sendRequestUsingPost(httpPost);

	}
}
