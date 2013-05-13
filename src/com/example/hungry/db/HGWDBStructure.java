package com.example.hungry.db;

import com.example.hungry.datatype.Restaurant;

import android.net.Uri;

public interface HGWDBStructure {
	public static final String SCHEME = "content://";
	public static final String AUTHORITY = "com.example.hungry.provider";
	public static final String _ID = "_id";
	
	public static class ContentURIs{
		public static final String USERS_PATH = "USERS";
		public static final Uri USERS_URI = Uri.parse(SCHEME + AUTHORITY + "/" + USERS_PATH);
		public static final int USERS_CODE = 1;
		
		
		public static final String RESTAURANTS_PATH = "RESTAURANTS";
		public static final Uri	RESTAURANTS_URI = Uri.parse(SCHEME + AUTHORITY + "/" + RESTAURANTS_PATH);
		public static final int RESTAURANTS_CODE = 2;
	}
	
	public static final String TABLE_RESTAURANTS = "RESTAURANTS";
	
	
	public static String TABLE_RESTAURANTS_CREATE = "CREATE TABLE "
			+ TABLE_RESTAURANTS + " (" + Restaurant.ID_COL + " INTEGER PRIMARY KEY,"
			+ Restaurant.NAME_COL + " TEXT,"
			+ Restaurant.NAME_SLUG_COL + " TEXT,"
			+ Restaurant.NAME_LOCAL_COL + " TEXT,"
			+ Restaurant.ADDRESS_STREET_COL + " TEXT,"
			+ Restaurant.ADDRESS_BUILDING_COL + " TEXT,"
			+ Restaurant.ADDRESS_BLOB_COL + " TEXT,"
			+ Restaurant.ADDRESS_REGION_COL + " TEXT,"
			+ Restaurant.ADDRESS_POSTAL_COL + " TEXT,"
			+ Restaurant.ADDRESS_LOCAL_STREET_COL + " TEXT,"
			+ Restaurant.ADDRESS_LOCAL_BUILDNG_COL + " TEXT,"
			+ Restaurant.ADDRESS_LOCAL_BLOB_COL + " TEXT,"
			+ Restaurant.ADDRESS_LOCAL_REGION_COL + " TEXT,"
			+ Restaurant.ADDRESS_LOCAL_POSTAL_COL + " TEXT,"	
			+ Restaurant.PHONE_COL + " TEXT,"
			+ Restaurant.FAX_COL + " TEXT,"
			+ Restaurant.EMAIL_COL + " TEXT,"
			+ Restaurant.OPERATING_HOUR_COL + " TEXT,"
			+ Restaurant.DESCRIPTION_SHORT_COL + " TEXT,"
			+ Restaurant.DESCRIPTION_FULL_COL + " TEXT,"
			+ Restaurant.URL_COL + " TEXT,"
			+ Restaurant.FACEBOOK_URL_COL + " TEXT,"
			+ Restaurant.TWITTER_URL_COL + " TEXT,"
			+ Restaurant.CITY_ID_COL + " INTEGER,"
			+ Restaurant.OPENED_DATE_COL + " TEXT,"
			+ Restaurant.CLOSED_DATE_COL + " TEXT,"
			+ Restaurant.ADDRESS_LATITUDE_COL + " REAL,"
			+ Restaurant.ADDRESS_LONGITUDE_COL + " REAL,"
			+ Restaurant.UPDATED_AT_COL + " INTEGER"
			+ ")";

}
