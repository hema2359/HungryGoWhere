package com.example.hungry.utils;

public class Const {
	
	public static final int PRODUCTION_MODE =1;
	public static final int STAGING_MODE =2;

	public static int MODE = STAGING_MODE;
	
	public static final String PRODUCTION_URL = "http://hungry-go-where.2359media.net";
	public static final String STAGING_URL = "http://hgwm2359-staging.herokuapp.com";

	public static String API_BASE_ADDRESS;
	public static String INTENT_REST_NAME = "intent_rest_name";
	static{
		API_BASE_ADDRESS = MODE == STAGING_MODE ? STAGING_URL : PRODUCTION_URL;
	}
}
