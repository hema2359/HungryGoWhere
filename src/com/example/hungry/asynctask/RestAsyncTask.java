package com.example.hungry.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hungry.datatype.Restaurant;
import com.example.hungry.db.HGWDBStructure.ContentURIs;
import com.example.hungry.helper.HttpHelper;
import com.example.hungry.utils.Const;

public class RestAsyncTask extends AsyncTask<Void,Void,Boolean>{
	Context context;
	private static final String TOTAL_ENTRIES = "total_entries";
	private static final String RESTAURANT_JSON  = "restaurants";
	private static final String TAG = "HUNGRY";
	ContentResolver contentResolver;
	
	
	public RestAsyncTask(Context context){
		this.context = context;
		this.contentResolver = context.getContentResolver();
	}
	@Override
	protected Boolean doInBackground(Void... params) {
		String requestUri = Const.API_BASE_ADDRESS + "/api/v1/restaurants";
		try{
			String response = HttpHelper.sendRequestUsingGet(requestUri, null);
			if(response != null){
				saveRestaurantData(response);
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		
	}
	
	private void saveRestaurantData(String jsondata) throws JSONException {
		contentResolver.delete(ContentURIs.RESTAURANTS_URI, null, null);
		List<ContentValues> listcv = new ArrayList<ContentValues>();
		JSONObject root = new JSONObject(jsondata);
		int total = root.optInt(TOTAL_ENTRIES);
		JSONArray restjson = root.optJSONArray(RESTAURANT_JSON);
		Log.d(TAG,"Total : "+total);
		JSONObject restaurant;
		for(int i=0;i<total;i++){
			restaurant = restjson.getJSONObject(i);
			ContentValues cv = Restaurant.getSimpleContentValue(restaurant);
			listcv.add(cv);
		}
		if(listcv.size() > 0)
			contentResolver.bulkInsert(ContentURIs.RESTAURANTS_URI, 
					listcv.toArray(new ContentValues[listcv.size()] ));
		
	}

}
