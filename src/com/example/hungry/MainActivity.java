package com.example.hungry;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungry.helper.HttpHelper;
import com.example.hungry.share.FacebookShare;
import com.example.hungry.share.SessionEvents;
import com.example.hungry.share.SessionStore;

public class MainActivity extends Activity {
	private FacebookShare facebookshare;
	private final String TAG = "HUNGRY";
	private final String HUNGRY_KEY = "hungry_session";
	private String mFbToken;
	private String mHgryToken;
	private Button mBtnLogin, mBtnOpen;
	private TextView mTvWelcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		facebookshare = new FacebookShare(this);
		mBtnLogin= (Button)findViewById(R.id.btn_login);
		mBtnOpen = (Button)findViewById(R.id.btn_open);
		mTvWelcome = (TextView)findViewById(R.id.tv_welcome);
		if(isSessionValid())
			showLogin(false);
		else
			showLogin(true);
		
	}

	private void showLogin(boolean bLogged) {
		if(bLogged){
			mBtnLogin.setVisibility(View.VISIBLE);
			mBtnOpen.setVisibility(View.GONE);
			mTvWelcome.setVisibility(View.GONE);
		}else{
			mBtnLogin.setVisibility(View.GONE);
			mBtnOpen.setVisibility(View.VISIBLE);
			mTvWelcome.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.menu_logout:
			logoutHungry();
			facebookshare.logout();
			showLogin(true);
			return true;
		default:
				return super.onOptionsItemSelected(item);
			
		}
	}
	
	public void onLogin(View v){
			if(!facebookshare.isSessionValid()){
				Log.d(TAG,"session not valid");
				loginToFacebook();				

			}
			else {
				Log.d(TAG,"facebook session valid");
				//getProfile();
				loginToHungry();
			}
		
		
	}
	public void onOpen(View v){
		openHungry();
	}
	private void logoutHungry() {
		SharedPreferences prefs = getSharedPreferences(HUNGRY_KEY,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("access_token", null);
		editor.commit();		
	}
	 private void openHungry() {
		 Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();
		 Intent i = new Intent(this, SecondActivity.class);
	 	 startActivity(i);		
	}

	private boolean isSessionValid() {
		 SharedPreferences prefs = getSharedPreferences(HUNGRY_KEY,Context.MODE_PRIVATE);
		 String token = prefs.getString("access_token", null);
		 Log.d(TAG,"token : "+token);
		 if(token != null)
			 return true;
		 else
			 return false;
	}

	private void loginToHungry() {
		 new LoginAsyncTask().execute();
		 Log.d(TAG, "logging to hungry");
		 
	}

	////////////////Facebook Sharing/////////////////
		private void loginToFacebook() {
			// add login callback
			SessionEvents.addAuthListener(new SessionEvents.AuthListener() {

				@Override
				public void onAuthSucceed() {
					Toast.makeText(MainActivity.this, "Login Succeed", Toast.LENGTH_SHORT).show();
					//getProfile();
					loginToHungry();
				}

				@Override
				public void onAuthFail(String error) {
					Toast.makeText(MainActivity.this, "Sorry, Login to facebook failed", Toast.LENGTH_SHORT).show();
				}
			});
			facebookshare.authorize();		
		}
		
		

		private void postToFacebook(Bundle data) {
			SessionEvents.addPostListener(new SessionEvents.PostListener() {

				@Override
				public void onPostSucceed() {
					Toast.makeText(MainActivity.this, "Post successful", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onPostFail() {
					Toast.makeText(MainActivity.this, "Post failed or have been cancelled", Toast.LENGTH_SHORT).show();				
				}

				@Override
				public void onPostError() {
					onPostFail();
				}
			});
			//facebookshare.showPostDialog(generatePostData(data));
		}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	//	if (facebookshare != null) {
		//	facebookshare.authorizeCallback(requestCode, resultCode, data);
		//}
	}
	class LoginAsyncTask extends AsyncTask<Void,Void,Boolean>{

		@Override
		protected Boolean doInBackground(Void... data) {
			// TODO Auto-generated method stub
			String requestUri = HttpHelper.API_BASE_ADDRESS + "oauth/token";
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(4);
			SharedPreferences prefs = getSharedPreferences(SessionStore.KEY, Context.MODE_PRIVATE);
			String token = prefs.getString(SessionStore.TOKEN, "");
			if(!token.equals("")){
			params.add(new BasicNameValuePair("grant_type", "password"));
			params.add(new BasicNameValuePair("facebook_access_token",token));
			params.add(new BasicNameValuePair("client_id","71e600bb887043e8abac68877989cc7fe757d7817a7fb8af313026670bf46043"));
			params.add(new BasicNameValuePair("client_secret","dc8c026cb89f0f2796be6c99c520d4369877feda2b8a73691f9c0f6e344f5099"));
			
			}
			Log.d(TAG,requestUri);
			try {
				String response = HttpHelper.sendRequestUsingPost(requestUri, params);
				Log.d(TAG, "response : "+response);
				if(response !=null)
					saveJsonData(response);
			} catch (Exception e) {
				return false;
			}
			return true;		
		}
		
		private void saveJsonData(String response) throws JSONException {
			JSONObject root = new JSONObject(response);
			mHgryToken = root.optString("access_token");
			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			SharedPreferences prefs = getSharedPreferences(HUNGRY_KEY,Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("access_token", mHgryToken);
			editor.commit();
			 Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

			//openHungry();
			showLogin(false);
		}
	}
}
