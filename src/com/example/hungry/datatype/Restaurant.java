package com.example.hungry.datatype;

import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

public class Restaurant {
	public String mId,mName,mAddrSt,mAddrBlg,mAddrBlob,mOperHour;
	
	
	public static final String ID_COL = "_id";
	public static final String NAME_COL = "name";
	public static final String NAME_SLUG_COL = "name_slug";
	public static final String NAME_LOCAL_COL = "name_local";
	public static final String ADDRESS_STREET_COL = "address_street";
	public static final String ADDRESS_BUILDING_COL = "address_building";
	public static final String ADDRESS_BLOB_COL = "address_blob";
	public static final String ADDRESS_REGION_COL = "address_region";
	public static final String ADDRESS_POSTAL_COL = "address_postal";
	public static final String ADDRESS_LOCAL_STREET_COL = "address_local_street";
	public static final String ADDRESS_LOCAL_BUILDNG_COL = "address_local_building";
	public static final String ADDRESS_LOCAL_BLOB_COL = "address_local_blob";
	public static final String ADDRESS_LOCAL_REGION_COL = "address_local_region";
	public static final String ADDRESS_LOCAL_POSTAL_COL = "address_local_postal";
	public static final String PHONE_COL = "phone";
	public static final String FAX_COL = "fax";
	public static final String EMAIL_COL = "email";
	public static final String OPERATING_HOUR_COL = "operating_hour";
	public static final String DESCRIPTION_SHORT_COL = "description_short";
	public static final String DESCRIPTION_FULL_COL = "description_full";
	public static final String URL_COL = "url";
	public static final String FACEBOOK_URL_COL = "facebook_url";
	public static final String TWITTER_URL_COL = "twitter_url";
	public static final String CITY_ID_COL = "city_id";
	public static final String OPENED_DATE_COL = "opened_date";
	public static final String CLOSED_DATE_COL = "closed_date";
	public static final String ADDRESS_LATITUDE_COL = "address_latitude";
	public static final String ADDRESS_LONGITUDE_COL = "address_longitude";
	public static final String UPDATED_AT_COL = "updated_at";
	
	public static final String DEFAULT_SORT_ORDER = ID_COL + " ASC";
	
	public static final String ID_JSON = "id";
	public static final String NAME_JSON = "name";
	public static final String NAME_SLUG_JSON = "name_slug";
	public static final String NAME_LOCAL_JSON = "name_local";
	public static final String ADDRESS_STREET_JSON = "address_street";
	public static final String ADDRESS_BUILDING_JSON = "address_building";
	public static final String ADDRESS_BLOB_JSON = "address_blob";
	public static final String ADDRESS_REGION_JSON = "address_region";
	public static final String ADDRESS_POSTAL_JSON = "address_postal";
	public static final String ADDRESS_LOCAL_STREET_JSON = "address_local_street";
	public static final String ADDRESS_LOCAL_BUILDNG_JSON = "address_local_building";
	public static final String ADDRESS_LOCAL_BLOB_JSON = "address_local_blob";
	public static final String ADDRESS_LOCAL_REGION_JSON = "address_local_region";
	public static final String ADDRESS_LOCAL_POSTAL_JSON = "address_local_postal";
	public static final String PHONE_JSON = "phone";
	public static final String FAX_JSON = "fax";
	public static final String EMAIL_JSON = "email";
	public static final String OPERATING_HOUR_JSON = "operating_hour";
	public static final String DESCRIPTION_SHORT_JSON = "description_short";
	public static final String DESCRIPTION_FULL_JSON = "description_full";
	public static final String URL_JSON = "url";
	public static final String FACEBOOK_URL_JSON = "facebook_url";
	public static final String TWITTER_URL_JSON = "twitter_url";
	public static final String CITY_ID_JSON = "city_id";
	public static final String OPENED_DATE_JSON = "opened_date";
	public static final String CLOSED_DATE_JSON = "closed_date";
	public static final String ADDRESS_LATITUDE_JSON = "address_latitude";
	public static final String ADDRESS_LONGITUDE_JSON = "address_longitude";
	public static final String UPDATED_AT_JSON = "updated_at";
	
	public Restaurant(Cursor c){
		this.mId = c.getString(0);
		this.mName = c.getString(1);
		this.mAddrSt = c.getString(4);
		this.mAddrBlg = c.getString(5);
		this.mAddrBlob = c.getString(6);
		this.mOperHour = c.getString(17);
	}
	
	public static ContentValues getSimpleContentValue(JSONObject data){
		ContentValues v = new ContentValues();
		v.put(ID_COL, data.optLong(ID_JSON));
		v.put(NAME_COL, data.optString(NAME_JSON));
		v.put(ADDRESS_STREET_COL,data.optString(ADDRESS_STREET_JSON));
		v.put(ADDRESS_BUILDING_COL, data.optString(ADDRESS_BUILDING_JSON));
		v.put(ADDRESS_BLOB_COL, data.optString(ADDRESS_BLOB_JSON));
		v.put(PHONE_COL, data.optString(PHONE_JSON));
		v.put(FAX_COL,data.optString(FAX_JSON));
		v.put(OPERATING_HOUR_COL, data.optString(OPERATING_HOUR_JSON));
		v.put(UPDATED_AT_COL, data.optLong(UPDATED_AT_JSON));
		return v;
	}
}
